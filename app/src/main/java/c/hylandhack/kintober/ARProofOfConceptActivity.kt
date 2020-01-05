package c.hylandhack.kintober

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_arproof_of_concept.*


class ARProofOfConceptActivity : AppCompatActivity(), Scene.OnUpdateListener, Runnable {

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    private var modelRenderable: ModelRenderable? = null

    //A method to find the screen center. This is used while placing objects in the scene
    var dest = Vector3()
    var destx = 41.469456
    var desty = -81.948889
    var x = 41.468391
    var y = -81.933355
    var offset: Vector3? = null
    val scale = 0.05f
    val sx = 89354.8579f
    val sy = 169934.6405f
    private var timerHandler: Handler? = null

    var modelAnchor: Anchor? = null

    var node: Node? = null
    var anode: AnchorNode? = null
    var anode2: AnchorNode? = null

    var pose: Pose? = null

    var bool = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arproof_of_concept)

        // When you build a Renderable, Sceneform loads its resources in the background while returning
       // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        timerHandler = Handler()
        offset = calcVector(x,y,destx, desty).scaled(scale)
        bool = true
        var kintoRender: ModelRenderable? = null
        ModelRenderable.builder()
            //get the context of the ARFragment and pass the name of your .sfb file
            .setSource(this, R.raw.kintober_ghost)
            .build()

            //I accepted the CompletableFuture using Async since I created my model on creation of the activity. You could simply use .thenAccept too.
            //Use the returned modelRenderable and save it to a global variable of the same name
            .thenAccept { modelRenderable -> this@ARProofOfConceptActivity.modelRenderable = modelRenderable }

        (ux_fragment as ArFragment).arSceneView.scene.addOnUpdateListener(this@ARProofOfConceptActivity)

        val session: Session? = (ux_fragment as ArFragment).getArSceneView().getSession()

        val position = floatArrayOf(0f, 0f, 0f)

        val rotation = floatArrayOf(0f, 0f, 0f, 1f)

        val anchor = session?.createAnchor(Pose(position, rotation))

        anode = AnchorNode(anchor).apply {
            setParent((ux_fragment as ArFragment).getArSceneView().getScene())
        }

    }

    private fun screenCenter(): Vector3 {
        val vw = findViewById<View>(android.R.id.content)
        return Vector3(vw.width / 2f, vw.height / 2f, 0f)
    }

    override fun onUpdate(frameTime: FrameTime?) {
        val frame = (ux_fragment as ArFragment).arSceneView.arFrame

        if (frame != null && bool) {
            //get the trackables to ensure planes are detected
            val var3 = frame.getUpdatedTrackables(Plane::class.java).iterator()
            while (var3.hasNext()) {
                val plane = var3.next() as Plane

                //If a plane has been detected & is being tracked by ARCore
                if (plane.trackingState == TrackingState.TRACKING) {

                    //Hide the plane discovery helper animation
                    (ux_fragment as ArFragment).planeDiscoveryController.hide()


                    //Get all added anchors to the frame
                    val iterableAnchor = frame.updatedAnchors.iterator()

                    //place the first object only if no previous anchors were added
                    if (!iterableAnchor.hasNext()) {
                        //Perform a hit test at the center of the screen to place an object without tapping
                        val hitTest = frame.hitTest(screenCenter().x, screenCenter().y)
                        //iterate through all hits
                        val hitTestIterator = hitTest.iterator()
                        bool = false
                        while (hitTestIterator.hasNext()) {
                            val hitResult = hitTestIterator.next()

                            //Create an anchor at the plane hit
                            if(node == null) {
                                val modelAnchor = plane.createAnchor(hitResult.hitPose)
                                //pose = modelAnchor.pose
                                //Attach a node to this anchor with the scene as the parent
                                val anchorNode = AnchorNode(modelAnchor)
                                anchorNode.setParent((ux_fragment as ArFragment).arSceneView.scene)

                                //create a new TranformableNode that will carry our object
                                node = Node().apply {
                                    setParent(anchorNode)
                                    renderable =
                                        this@ARProofOfConceptActivity.modelRenderable
                                    worldPosition = Vector3(
                                        modelAnchor.pose.tx(),
                                        modelAnchor.pose.compose(
                                            Pose.makeTranslation(
                                                0f,
                                                0f,
                                                0f
                                            )
                                        ).ty(),
                                        modelAnchor.pose.tz())
                                }
                                anode2 = anchorNode
                                /*
                                node.setParent(anchorNode)
                                node.renderable =
                                    this@ARProofOfConceptActivity.modelRenderable

                                //Alter the real world position to ensure object renders on the table top. Not somewhere inside.
                                node.worldPosition = Vector3(
                                    modelAnchor.pose.tx(),
                                    modelAnchor.pose.compose(
                                        Pose.makeTranslation(
                                            0f,
                                            0.05f,
                                            0f
                                        )
                                    ).ty(),
                                    modelAnchor.pose.tz()*/
                            }
                        }
                    }
                }
            }
        }
    }

    fun calcVector(x: Double, y: Double, dx: Double, dy: Double): Vector3{
        return Vector3(((dy-y)*sx).toFloat(), 0f, ((dx-x)*sy).toFloat())
    }

    override fun run() {
        /*offset?.let {
            val temp = pose?.inverse()?.rotateVector(floatArrayOf(it.x, it.y, it.z))
            if(temp != null) {
                val temp2 = Vector3.add(node?.localPosition, Vector3(temp[0],temp[1], temp[2]))
                node?.localPosition = temp2
            }
        }*/
        node?.localPosition = Vector3.lerp(node?.localPosition, Vector3(0f,-1f, 0f), scale)
        //val temp = pose.rotateVector(floatArrayOf(offset?.x, offset?.y, offset?.z))
        //val temp2 = node?.worldToLocalDirection(offset)
        //val temp = Vector3.add(anode?.worldPosition, offset)
        //val temp = Vector3.add((ux_fragment as ArFragment).transformationSystem.selectedNode?.worldPosition, offset?.scaled(scale))
        //anode?.worldPosition = temp
        //(ux_fragment as ArFragment).transformationSystem.selectedNode?.worldPosition = temp
        //(ux_fragment as ArFragment).transformationSystem.selectedNode?.worldPosition = Vector3.lerp(Vector3(0f,0f,0f), offset, scale)
        timerHandler?.postDelayed(this, 50)
    }

    fun click(v: View){
        Log.d("Forward", node.toString())
        //node?.localRotation = Quaternion.axisAngle(Vector3(0f,1f,0f), 40f)
        node?.setParent(anode)
        node?.localPosition = anode2?.localPosition
        timerHandler?.postDelayed(this, 0)
    }
}
