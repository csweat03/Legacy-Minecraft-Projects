package club.shmoke.client.util.render;

import java.util.ArrayList;
import java.util.Random;

public class ParticleManager {

	public double x, y, mX, mY;

	public ParticleManager(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void update(ArrayList<ParticleManager> particles) {
		if(x < 0) x = RenderUtils.getResolution().getScaledWidth();
		if(y < 0) y = RenderUtils.getResolution().getScaledHeight();
		if(x > RenderUtils.getResolution().getScaledWidth()) x = 0;
		if(y > RenderUtils.getResolution().getScaledHeight()) y = 0;

		x += mX;
		y += mY;

		mX *= 1;
		mY *= 1;
	}

	public ParticleManager changeMotion(double motionX, double motionY) {
		mX += motionX;
		mY += motionY;
		return this;
	}

}