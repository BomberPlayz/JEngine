package hu.bomberplayz.jengine.ui;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;


public class Renderer {
    // Draws a rounded filled rectangle.
    // args: x, y, width, height, rounding (aka corner-size), color

    public static void drawRoundedCorner(double x, double y, double sa, double arc, float r) {
        float cent_x = (float) (x + r * Math.cos(sa + 3.141592653589793D / 2D));
        float cent_y = (float) (y + r * Math.sin(sa + 3.141592653589793D / 2D));

        // build up the piecemeal including end of the arc
        int n = (int) Math.ceil(32 * arc / 3.141592653589793D * 2);
        for(int i=0; i<=n; i++) {
            double ang = sa + arc * (double)i / (double)n;

            // compute the next point
            float x1 = (float) (cent_x + r * Math.sin(ang));
            float y1 = (float) (cent_y - r * Math.cos(ang));
            GL11.glVertex2f(x1, y1);
        }
    }

    public static ArrayList<Float> drawRoundedCornerFake(double x, double y, double sa, double arc, float r) {
        float cent_x = (float) (x + r * Math.cos(sa + 3.141592653589793D / 2D));
        float cent_y = (float) (y + r * Math.sin(sa + 3.141592653589793D / 2D));

        // build up the piecemeal including end of the arc
        int n = (int) Math.ceil(32 * arc / 3.141592653589793D * 2);
        ArrayList<Float> points = new ArrayList<Float>();
        for(int i=0; i<=n; i++) {
            double ang = sa + arc * (double)i / (double)n;

            // compute the next point
            float x1 = (float) (cent_x + r * Math.sin(ang));
            float y1 = (float) (cent_y - r * Math.cos(ang));
            points.add(x1);
            points.add(y1);


        }
        return points;
    }





    public static void drawFilledRoundedRect(double x, double y, double width, double height, int rounding, Color color) {
        GL11.glPushMatrix();
        boolean texture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean lighting = GL11.glIsEnabled(GL11.GL_LIGHTING);

        if (texture) GL11.glDisable(GL11.GL_TEXTURE_2D);
        if (!blend) GL11.glEnable(GL11.GL_BLEND);
        if (lighting) GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);




        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);


        ArrayList<Float> recsapta = new ArrayList<Float>();

        recsapta.addAll(drawRoundedCornerFake(x, y+rounding, 3*Math.PI/2, Math.PI/2, rounding));
        recsapta.addAll(drawRoundedCornerFake(x+width-rounding, y, 0, Math.PI/2, rounding));
        recsapta.addAll(drawRoundedCornerFake(x+width, y+height-rounding, Math.PI / 2, Math.PI/2, rounding));
        recsapta.addAll(drawRoundedCornerFake(x+rounding, y+height, Math.PI, Math.PI/2, rounding));





        GL11.glBegin(GL11.GL_POLYGON);


        // loop through the points backwards

        for(int i=recsapta.size()-2; i>=0; i-=2) {
            GL11.glVertex2f(recsapta.get(i), recsapta.get(i+1));
        }

        GL11.glEnd();

        if (texture) GL11.glEnable(GL11.GL_TEXTURE_2D);
        if (!blend) GL11.glDisable(GL11.GL_BLEND);
        if (lighting) GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glColor3f(1, 1, 1);
        GL11.glPopMatrix();
    }

    public static void drawFilledRoundedRect(double x, double y, int width, int height, int rounding, Color color, boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

        boolean texture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean lighting = GL11.glIsEnabled(GL11.GL_LIGHTING);

        if (texture) GL11.glDisable(GL11.GL_TEXTURE_2D);
        if (!blend) GL11.glEnable(GL11.GL_BLEND);
        if (lighting) GL11.glDisable(GL11.GL_LIGHTING);
        // disable alpha test
        GL11.glDisable(GL11.GL_ALPHA_TEST);


        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);




        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);


        ArrayList<Float> recsapta = new ArrayList<Float>();


        if(topLeft) {
            recsapta.addAll(drawRoundedCornerFake(x, y+rounding, 3*Math.PI/2, Math.PI/2, rounding));
        } else {
            recsapta.add((float) x);
            recsapta.add((float) y);
        }

        if(topRight) {
            recsapta.addAll(drawRoundedCornerFake(x+width-rounding, y, 0, Math.PI/2, rounding));
        } else {
            recsapta.add((float) x+width);
            recsapta.add((float) y);
        }

        if(bottomRight) {
            recsapta.addAll(drawRoundedCornerFake(x+width, y+height-rounding, Math.PI / 2, Math.PI/2, rounding));
        } else {
            recsapta.add((float) x+width);
            recsapta.add((float) y+height);
        }

        if(bottomLeft) {
            recsapta.addAll(drawRoundedCornerFake(x+rounding, y+height, Math.PI, Math.PI/2, rounding));
        } else {
            recsapta.add((float) x);
            recsapta.add((float) y+height);
        }







        GL11.glBegin(GL11.GL_POLYGON);


        // loop through the points backwards

        for(int i=recsapta.size()-2; i>=0; i-=2) {
            GL11.glVertex2f(recsapta.get(i), recsapta.get(i+1));
        }

        GL11.glEnd();

        if (texture) GL11.glEnable(GL11.GL_TEXTURE_2D);
        if (!blend) GL11.glDisable(GL11.GL_BLEND);
        if (lighting) GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor3f(1, 1, 1);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(double x, double y, int radius, Color color) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        GL11.glBegin(GL11.GL_POLYGON);
        for(int i=32; i>0; i--) {
            double angle = (double)i / 32.0 * 2.0 * Math.PI;
            GL11.glVertex2d(x + radius * Math.cos(angle), y + radius * Math.sin(angle));
        }
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        GL11.glPopAttrib();
        GL11.glPopMatrix();

    }

    public static void drawRect(double x, double y, double w, double h, Color color) {
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + h);
        GL11.glVertex2d(x + w, y + h);
        GL11.glVertex2d(x + w, y);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(1.0f, 1.0f, 1.0f);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }


    public static Color Blend(Color clOne, Color clTwo, float fAmount) {
        float fInverse = (float) (1.0 - fAmount);

        // Google is good :)
        float afOne[] = new float[3];
        clOne.getColorComponents(afOne);
        float afTwo[] = new float[3];
        clTwo.getColorComponents(afTwo);

        float afResult[] = new float[3];
        afResult[0] = afOne[0] * fAmount + afTwo[0] * fInverse;
        afResult[1] = afOne[1] * fAmount + afTwo[1] * fInverse;
        afResult[2] = afOne[2] * fAmount + afTwo[2] * fInverse;

        return new Color (afResult[0], afResult[1], afResult[2]);
    }

    public static double animate(double start, double end, double time, double duration) {
        return start + (end - start) * (time / duration);
    }

    // animate with ease

    public static double animateEase(double start, double end, double time, double duration) {
        // ease in and out
        double t = time / duration;
        double d = end - start;
        return start + d * (t * t * t * (t * (t * 6 - 15) + 10)); // fine for now lol
    }




}