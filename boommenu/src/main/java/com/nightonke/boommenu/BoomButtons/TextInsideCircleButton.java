package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.nightonke.boommenu.R;

import java.util.ArrayList;

/**
 * Created by Weiping Huang at 22:44 on 16/11/23
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */

public class TextInsideCircleButton extends BoomButton {

    protected TextInsideCircleButton(Builder builder, Context context) {
        super(context);
        this.context = context;
        init(builder);
    }

    private void init(Builder builder) {
        LayoutInflater.from(context).inflate(R.layout.bmb_text_inside_circle_button, this, true);
        initAttrs(builder);
        initShadow(buttonRadius + shadowRadius);
        initCircleButton();
        initText(button);
        initImage();
        centerPoint = new PointF(
                buttonRadius + shadowRadius + shadowOffsetX,
                buttonRadius + shadowRadius + shadowOffsetY);
    }

    private void initAttrs(Builder builder) {
        super.initAttrs(builder);
    }

    @Override
    public ArrayList<View> goneViews() {
        ArrayList<View> goneViews = new ArrayList<>();
        goneViews.add(image);
        goneViews.add(text);
        return goneViews;
    }

    @Override
    public ArrayList<View> rotateViews() {
        ArrayList<View> rotateViews = new ArrayList<>();
        if (rotateImage) rotateViews.add(image);
        if (rotateText) rotateViews.add(text);
        return rotateViews;
    }

    @Override
    public int trueWidth() {
        return buttonRadius * 2 + shadowRadius * 2 + shadowOffsetX * 2;
    }

    @Override
    public int trueHeight() {
        return buttonRadius * 2 + shadowRadius * 2 + shadowOffsetY * 2;
    }

    @Override
    public int contentWidth() {
        return buttonRadius * 2;
    }

    @Override
    public int contentHeight() {
        return buttonRadius * 2;
    }

    @Override
    public void toPress() {
        if (lastStateIsNormal && ableToHighlight) {
            toPressImage();
            toPressText();
            lastStateIsNormal = false;
        }
    }

    @Override
    public void toNormal() {
        if (!lastStateIsNormal) {
            toNormalImage();
            toNormalText();
            lastStateIsNormal = true;
        }
    }

    @Override
    public void setRotateAnchorPoints() {
        image.setPivotX(buttonRadius - imageRect.left);
        image.setPivotY(buttonRadius - imageRect.top);
        text.setPivotX(buttonRadius - textRect.left);
        text.setPivotY(buttonRadius - textRect.top);
    }

    @Override
    public void setSelfScaleAnchorPoints() {

    }

    /**
     * Builder of text-inside-circle boom button.
     * You can use this builder to set:
     * rotateImage,
     * rotateText,
     * shadowEffect,
     * shadowOffsetX,
     * shadowOffsetY,
     * shadowRadius,
     * shadowColor,
     * normalImageRes,
     * highlightedImageRes,
     * unableImageRes,
     * normalImageDrawable,
     * highlightedImageDrawable,
     * unableImageRes,
     * imageRect,
     * imagePadding,
     * normalTextRes,
     * highlightedTextRes,
     * unableTextRes,
     * normalText,
     * highlightedText,
     * unableText,
     * normalTextColor,
     * highlightedTextColor,
     * unableTextColor,
     * textRect,
     * textPadding,
     * typeface,
     * maxLines,
     * textGravity,
     * ellipsize,
     * textSize,
     * rippleEffect,
     * normalColor,
     * highlightedColor,
     * rippleColor,
     * unableColor,
     * unable,
     * buttonRadius.
     */
    public static class Builder extends BoomButtonBuilder {

        //region setters

        /**
         * Set the index of the boom-button, don't use this method.
         *
         * @param index the index
         * @return the builder
         */
        public Builder index(int index) {
            this.index = index;
            return this;
        }

        /**
         * Set the listener of the boom-button, don't use this method.
         *
         * @param listener the listener
         * @return the builder
         */
        public Builder innerListener(InnerOnBoomButtonClickListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Set listener for when the boom-button is clicked.
         *
         * @param onBMClickListener OnBMClickListener
         * @return the builder
         */
        public Builder listener(OnBMClickListener onBMClickListener) {
            this.onBMClickListener = onBMClickListener;
            return this;
        }

        /**
         * Whether the image-view should rotate.
         *
         * @param rotateImage rotate or not
         * @return the builder
         */
        public Builder rotateImage(boolean rotateImage) {
            this.rotateImage = rotateImage;
            return this;
        }

        /**
         * Whether the text-view should rotate.
         *
         * @param rotateText rotate or not
         * @return the builder
         */
        public Builder rotateText(boolean rotateText) {
            this.rotateText = rotateText;
            return this;
        }

        /**
         * Whether the boom-button should have a shadow effect.
         *
         * @param shadowEffect have shadow effect or not
         * @return the builder
         */
        public Builder shadowEffect(boolean shadowEffect) {
            this.shadowEffect = shadowEffect;
            return this;
        }

        /**
         * Set the horizontal shadow-offset of the boom-button.
         *
         * @param shadowOffsetX the shadow offset x
         * @return the builder
         */
        public Builder shadowOffsetX(int shadowOffsetX) {
            this.shadowOffsetX = shadowOffsetX;
            return this;
        }

        /**
         * Set the vertical shadow-offset of the boom-button.
         *
         * @param shadowOffsetY the shadow offset y
         * @return the builder
         */
        public Builder shadowOffsetY(int shadowOffsetY) {
            this.shadowOffsetY = shadowOffsetY;
            return this;
        }

        /**
         * Set the radius of shadow of the boom-button.
         *
         * @param shadowRadius the shadow radius
         * @return the builder
         */
        public Builder shadowRadius(int shadowRadius) {
            this.shadowRadius = shadowRadius;
            return this;
        }

        /**
         * Set the color of the shadow of boom-button.
         *
         * @param shadowColor the shadow color
         * @return the builder
         */
        public Builder shadowColor(int shadowColor) {
            this.shadowColor = shadowColor;
            return this;
        }

        /**
         * Set the image resource when boom-button is at normal-state.
         *
         * @param normalImageRes the normal image res
         * @return the builder
         */
        public Builder normalImageRes(int normalImageRes) {
            this.normalImageRes = normalImageRes;
            return this;
        }

        /**
         * Set the image resource when boom-button is at highlighted-state.
         *
         * @param highlightedImageRes the highlighted image res
         * @return the builder
         */
        public Builder highlightedImageRes(int highlightedImageRes) {
            this.highlightedImageRes = highlightedImageRes;
            return this;
        }

        /**
         * Set the image resource when boom-button is at unable-state.
         *
         * @param unableImageRes the unable image res
         * @return the builder
         */
        public Builder unableImageRes(int unableImageRes) {
            this.unableImageRes = unableImageRes;
            return this;
        }

        /**
         * Set the image drawable when boom-button is at normal-state.
         *
         * @param normalImageDrawable the normal image drawable
         * @return the builder
         */
        public Builder normalImageDrawable(Drawable normalImageDrawable) {
            this.normalImageDrawable = normalImageDrawable;
            return this;
        }

        /**
         * Set the image drawable when boom-button is at highlighted-state.
         *
         * @param highlightedImageDrawable the highlighted image drawable
         * @return the builder
         */
        public Builder highlightedImageDrawable(Drawable highlightedImageDrawable) {
            this.highlightedImageDrawable = highlightedImageDrawable;
            return this;
        }

        /**
         * Set the image drawable when boom-button is at unable-state.
         *
         * @param unableImageDrawable the unable image drawable
         * @return the builder
         */
        public Builder unableImageDrawable(Drawable unableImageDrawable) {
            this.unableImageDrawable = unableImageDrawable;
            return this;
        }

        /**
         * Set the rect of image.
         * By this method, you can set the position and size of the image-view in boom-button.
         * For example, builder.imageRect(new Rect(0, 50, 100, 100)) will make the
         * image-view's size to be 100 * 50 and margin-top to be 50 pixel.
         *
         * @param imageRect the image rect, in pixel.
         * @return the builder
         */
        public Builder imageRect(Rect imageRect) {
            this.imageRect = imageRect;
            return this;
        }

        /**
         * Set the padding of image.
         * By this method, you can control the padding in the image-view.
         * For instance, builder.imagePadding(new Rect(10, 10, 10, 10)) will make the
         * image-view content 10-pixel padding to itself.
         *
         * @param imagePadding the image padding
         * @return the builder
         */
        public Builder imagePadding(Rect imagePadding) {
            this.imagePadding = imagePadding;
            return this;
        }

        /**
         * Set the text resource when boom-button is at normal-state.
         *
         * @param normalTextRes text resource
         * @return the builder
         */
        public Builder normalTextRes(int normalTextRes) {
            this.normalTextRes = normalTextRes;
            return this;
        }

        /**
         * Set the text resource when boom-button is at highlighted-state.
         *
         * @param highlightedTextRes text resource
         * @return the builder
         */
        public Builder highlightedTextRes(int highlightedTextRes) {
            this.highlightedTextRes = highlightedTextRes;
            return this;
        }

        /**
         * Set the text resource when boom-button is at unable-state.
         *
         * @param unableTextRes text resource
         * @return the builder
         */
        public Builder unableTextRes(int unableTextRes) {
            this.unableTextRes = unableTextRes;
            return this;
        }

        /**
         * Set the text when boom-button is at normal-state.
         *
         * @param normalText text
         * @return the builder
         */
        public Builder normalText(String normalText) {
            this.normalText = normalText;
            return this;
        }

        /**
         * Set the text when boom-button is at highlighted-state.
         *
         * @param highlightedText text
         * @return the builder
         */
        public Builder highlightedText(String highlightedText) {
            this.highlightedText = highlightedText;
            return this;
        }

        /**
         * Set the text when boom-button is at unable-state.
         *
         * @param unableText text
         * @return the builder
         */
        public Builder unableText(String unableText) {
            this.unableText = unableText;
            return this;
        }

        /**
         * Set the color of text when boom-button is at normal-state.
         *
         * @param normalTextColor color of text
         * @return the builder
         */
        public Builder normalTextColor(int normalTextColor) {
            this.normalTextColor = normalTextColor;
            return this;
        }

        /**
         * Set the color of text when boom-button is at highlighted-state.
         *
         * @param highlightedTextColor color of text
         * @return the builder
         */
        public Builder highlightedTextColor(int highlightedTextColor) {
            this.highlightedTextColor = highlightedTextColor;
            return this;
        }

        /**
         * Set the color of text when boom-button is at unable-state.
         *
         * @param unableTextColor color the text
         * @return the builder
         */
        public Builder unableTextColor(int unableTextColor) {
            this.unableTextColor = unableTextColor;
            return this;
        }

        /**
         * Set the rect of text.
         * By this method, you can set the position and size of the text-view in boom-button.
         * For example, builder.textRect(new Rect(0, 50, 100, 100)) will make the
         * text-view's size to be 100 * 50 and margin-top to be 50 pixel.
         *
         * @param textRect the text rect, in pixel.
         * @return the builder
         */
        public Builder textRect(Rect textRect) {
            this.textRect = textRect;
            return this;
        }

        /**
         * Set the padding of text.
         * By this method, you can control the padding in the text-view.
         * For instance, builder.textPadding(new Rect(10, 10, 10, 10)) will make the
         * text-view content 10-pixel padding to itself.
         *
         * @param textPadding the text padding
         * @return the builder
         */
        public Builder textPadding(Rect textPadding) {
            this.textPadding = textPadding;
            return this;
        }

        /**
         * Set the typeface of the text.
         *
         * @param typeface typeface
         * @return the builder
         */
        public Builder typeface(Typeface typeface) {
            this.typeface = typeface;
            return this;
        }

        /**
         * Set the maximum of the lines of text-view.
         *
         * @param maxLines maximum lines
         * @return the builder
         */
        public Builder maxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        /**
         * Set the gravity of text-view.
         *
         * @param gravity gravity, for example, Gravity.CENTER
         * @return the builder
         */
        public Builder textGravity(int gravity) {
            this.textGravity = gravity;
            return this;
        }

        /**
         * Set the ellipsize of the text-view.
         *
         * @param ellipsize ellipsize
         * @return the builder
         */
        public Builder ellipsize(TextUtils.TruncateAt ellipsize) {
            this.ellipsize = ellipsize;
            return this;
        }

        /**
         * Set the text size of the text-view.
         *
         * @param textSize size of text, in sp
         * @return the builder
         */
        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        /**
         * Whether the boom-button should have a ripple effect.
         *
         * @param rippleEffect the ripple effect
         * @return the builder
         */
        public Builder rippleEffect(boolean rippleEffect) {
            this.rippleEffect = rippleEffect;
            return this;
        }

        /**
         * The color of boom-button when it is at normal-state.
         *
         * @param normalColor the normal color
         * @return the builder
         */
        public Builder normalColor(int normalColor) {
            this.normalColor = normalColor;
            return this;
        }

        /**
         * The resource of color of boom-button when it is at normal-state.
         *
         * @param normalColorRes resource of normal color
         * @return the builder
         */
        public Builder normalColorRes(int normalColorRes) {
            this.normalColorRes = normalColorRes;
            return this;
        }

        /**
         * The color of boom-button when it is at highlighted-state.
         *
         * @param highlightedColor the highlighted color
         * @return the builder
         */
        public Builder highlightedColor(int highlightedColor) {
            this.highlightedColor = highlightedColor;
            return this;
        }

        /**
         * The resource of color of boom-button when it is at highlighted-state.
         *
         * @param highlightedColorRes resource of highlighted color
         * @return the builder
         */
        public Builder highlightedColorRes(int highlightedColorRes) {
            this.highlightedColorRes = highlightedColorRes;
            return this;
        }

        /**
         * The color of boom-button when it is at unable-state.
         *
         * @param unableColor the unable color
         * @return the builder
         */
        public Builder unableColor(int unableColor) {
            this.unableColor = unableColor;
            return this;
        }

        /**
         * The resource of color of boom-button when it is at unable-state.
         *
         * @param unableColorRes resource of unable color
         * @return the builder
         */
        public Builder unableColorRes(int unableColorRes) {
            this.unableColorRes = unableColorRes;
            return this;
        }

        /**
         * The color of boom-button when it is just a piece.
         *
         * @param pieceColor color of piece
         * @return the builder
         */
        public TextInsideCircleButton.Builder pieceColor(int pieceColor) {
            this.pieceColor = pieceColor;
            return this;
        }

        /**
         * The resource of color of boom-button when it is just a piece.
         *
         * @param pieceColorRes resource of color of piece
         * @return the builder
         */
        public Builder pieceColorRes(int pieceColorRes) {
            this.pieceColorRes = pieceColorRes;
            return this;
        }

        /**
         * Whether the boom-button is unable, default value is false.
         *
         * @param unable the unable
         * @return the builder
         */
        public Builder unable(boolean unable) {
            this.unable = unable;
            return this;
        }

        /**
         * The radius of boom-button, in pixel.
         *
         * @param buttonRadius the button radius
         * @return the builder
         */
        public Builder buttonRadius(int buttonRadius) {
            this.buttonRadius = buttonRadius;
            return this;
        }

        //endregion

        //region getters

        /**
         * Gets button radius.
         *
         * @return the button radius
         */
        public int getButtonRadius() {
            return buttonRadius;
        }

        //endregion

        /**
         * Build text-inside circle button, don't use this method.
         *
         * @param context the context
         * @return the simple circle button
         */
        public TextInsideCircleButton build(Context context) {
            return new TextInsideCircleButton(this, context);
        }
    }
}
