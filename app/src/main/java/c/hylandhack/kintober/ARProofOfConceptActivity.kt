package c.hylandhack.kintober

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arproof_of_concept.*

class ARProofOfConceptActivity : AppCompatActivity(), Scene.OnUpdateListener, Runnable {

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    private var modelRenderable: ModelRenderable? = null

    //A method to find the screen center. This is used while placing objects in the scene
    var destx = 41.469456
    var desty = -81.948889
    var x = 41.468391
    var y = -81.933355
    var offset: Vector3? = null
    val scale = 0.05f
    private var timerHandler: Handler? = null

    var bool = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arproof_of_concept)

        // When you build a Renderable, Sceneform loads its resources in the background while returning
       // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        timerHandler = Handler()
        offset = calcVector(x,y,destx, desty)
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
                            val modelAnchor = plane.createAnchor(hitResult.hitPose)

                            //Attach a node to this anchor with the scene as the parent
                            val anchorNode = AnchorNode(modelAnchor)
                            anchorNode.setParent((ux_fragment as ArFragment).arSceneView.scene)

                            //create a new TranformableNode that will carry our object
                            val transformableNode = TransformableNode((ux_fragment as ArFragment).transformationSystem)
                            transformableNode.setParent(anchorNode)
                            transformableNode.renderable = this@ARProofOfConceptActivity.modelRenderable
                            transformableNode.select()

                            //Alter the real world position to ensure object renders on the table top. Not somewhere inside.
                            transformableNode.worldPosition = Vector3(
                                modelAnchor.pose.tx(),
                                modelAnchor.pose.compose(Pose.makeTranslation(0f, 0.05f, 0f)).ty(),
                                modelAnchor.pose.tz()
                            )
                        }
                    }
                }
            }
        }
    }

    fun calcVector(x: Double, y: Double, dx: Double, dy: Double): Vector3{
        return Vector3((dx-x).toFloat(), 0f, (dy-y).toFloat()).normalized()
    }

    override fun run() {
        val temp = Vector3.add((ux_fragment as ArFragment).transformationSystem.selectedNode?.worldPosition, offset?.scaled(scale))
        (ux_fragment as ArFragment).transformationSystem.selectedNode?.worldPosition = temp
        timerHandler?.postDelayed(this, 50)
    }

    fun click(v: View){
        timerHandler?.postDelayed(this, 0)
    }
}
