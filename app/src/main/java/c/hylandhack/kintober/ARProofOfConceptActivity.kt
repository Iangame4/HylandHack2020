package c.hylandhack.kintober

import android.os.Bundle
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment.OnTapArPlaneListener
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_arproof_of_concept.*

class ARProofOfConceptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arproof_of_concept)

        // When you build a Renderable, Sceneform loads its resources in the background while returning
       // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        var kintoRender: ModelRenderable? = null;
        ModelRenderable.builder()
            .setSource(this, R.raw.kintober_ghost)
            .build()
            .thenAccept { renderable: ModelRenderable ->
                kintoRender = renderable
            }
            .exceptionally { throwable: Throwable? ->
                val toast =
                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                null
            }



        (ux_fragment as ArFragment).setOnTapArPlaneListener{
                hitResult: HitResult, plane: Plane?, motionEvent: MotionEvent? ->
                if (kintoRender == null) {
                    return@setOnTapArPlaneListener
                }
                // Create the Anchor.
                val anchor = hitResult.createAnchor()
                val anchorNode =
                    AnchorNode(anchor)
                anchorNode.setParent((ux_fragment as ArFragment).arSceneView.scene)
                // Create the transformable andy and add it to the anchor.
                val andy = TransformableNode((ux_fragment as ArFragment).getTransformationSystem())
                andy.setParent(anchorNode)
                andy.setRenderable(kintoRender)
                andy.select()
            }
    }
}
