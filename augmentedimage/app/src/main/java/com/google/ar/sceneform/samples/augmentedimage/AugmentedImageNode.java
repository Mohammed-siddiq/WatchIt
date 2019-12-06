/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.ar.sceneform.samples.augmentedimage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Node for rendering an augmented model. The image is augmented by placing the virtual watch model
 * on top of the image trackable.
 */
@SuppressWarnings({"AndroidApiChecker"})
public class AugmentedImageNode extends AnchorNode {

  private static final String TAG = "NAGANATH";

  // The augmented image represented by this node.
  private AugmentedImage image;
  private Map<String, CompletableFuture<ModelRenderable>> cache;

  private Node watchNode;
  private String latestModel;
  private CompletableFuture<ModelRenderable> watchRenderable;

  public AugmentedImageNode(Context context, String modelFile) {

    if(modelFile == null) {
      modelFile = "model_4";
    }
    cache = new HashMap<>();
    Log.e(TAG, "AugmentedImageNode: " + modelFile );
    setNewRenderable(context, modelFile);
  }

  public void setNewRenderable(Context context, String model) {
    model =  model +  ".sfb";
    latestModel = model;
    if(!cache.containsKey(latestModel)) {
      watchRenderable =
              ModelRenderable.builder()
                      .setSource(context, Uri.parse(model))
                      .build();
      if (watchRenderable.isDone()) {
        cache.put(latestModel, watchRenderable);
      }
    } else {
      watchRenderable = cache.get(latestModel);
    }
  }

  /**
   * Called when the AugmentedImage is detected and should be rendered. A Sceneform node tree is
   * created based on an Anchor created from the image.
   */
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  public void setImage(AugmentedImage image) {
    this.image = image;

    if(!watchRenderable.isDone()) {
      CompletableFuture.allOf(watchRenderable)
              .thenAccept((Void aVoid) -> setImage(image))
              .exceptionally(
                      throwable -> {
                        Log.e(TAG, "Exception loading", throwable);
                        return null;
                      });
      return;
    }

    watchNode = new Node();
    watchNode.setParent(this);
    watchNode.setRenderable(watchRenderable.getNow(null));
    // Set the anchor based on the center of the image.
    setAnchor(image.createAnchor(image.getCenterPose()));



    float maze_scale = 0.1f;

    watchNode.setLocalScale(new Vector3(maze_scale  , maze_scale* 0.5f, maze_scale ));
    watchNode.setLocalRotation(new Quaternion(new Vector3(0, 90, 0)));
    watchNode.setLocalPosition(new Vector3(0, 0f, 0));

  }

  public AugmentedImage getImage() {
    return image;
  }
}
