package com.fmtech.fmdianping.util.animation;

import android.graphics.Camera;
import android.view.animation.Animation;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/11 21:30
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/11 21:30  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class Rotate3dAnimation extends Animation{

    private Camera mCamera;
    private float mCenterX;
    private float mCenterY;
    private float mDepthZ;
    private float mFromDegrees;
    private boolean mReverse;
    private float mToDegrees;

    /**
     public Rotate3dAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, boolean paramBoolean)
     {
     this.mFromDegrees = paramFloat1;
     this.mToDegrees = paramFloat2;
     this.mCenterX = paramFloat3;
     this.mCenterY = paramFloat4;
     this.mDepthZ = paramFloat5;
     this.mReverse = paramBoolean;
     }

     protected void applyTransformation(float paramFloat, Transformation paramTransformation)
     {
     float f1 = this.mFromDegrees;
     float f2 = f1 + paramFloat * (this.mToDegrees - f1);
     float f3 = this.mCenterX;
     float f4 = this.mCenterY;
     Camera localCamera = this.mCamera;
     Matrix localMatrix = paramTransformation.getMatrix();
     localCamera.save();
     if (this.mReverse) {
     localCamera.translate(0.0F, 0.0F, paramFloat * this.mDepthZ);
     }
     for (;;)
     {
     localCamera.rotateY(f2);
     localCamera.getMatrix(localMatrix);
     localCamera.restore();
     localMatrix.preTranslate(-f3, -f4);
     localMatrix.postTranslate(f3, f4);
     return;
     localCamera.translate(0.0F, 0.0F, this.mDepthZ * (1.0F - paramFloat));
     }
     }

     public void initialize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
     {
     super.initialize(paramInt1, paramInt2, paramInt3, paramInt4);
     this.mCamera = new Camera();
     }
     */
}
