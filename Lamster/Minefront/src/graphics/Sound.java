package graphics;
import javax.sound.sampled.*;
public class Sound {		//let game have sound effect while playing
	public static Sound altar = loadSound("/snd/altar.wav");
	public static Sound bosskill = loadSound("/snd/bosskill.wav");
	public static Sound NGGYU = loadSound("/snd/NGGYU.wav");
	public static Sound cave5 = loadSound("/snd/cave5.wav");
	public static Sound nuance1 = loadSound("/snd/nuance1.wav");
	public static Sound click = loadSound("/snd/click.wav");
	public static Sound hal4 = loadSound("/snd/hal4.wav" );
	
	public static Sound loadSound(String fileName) {		//load sound from /snd folder
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}

	private Clip clip;

	public void play() {		//play sound
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}