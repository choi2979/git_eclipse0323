package thread.talk3;

import java.awt.Color;

import javax.swing.JPanel;

public class SettingRoom extends JPanel {
	TalkClientVer2 tc = null;
	public SettingRoom(TalkClientVer2 tc) {
		initDisplay();
	}

	private void initDisplay() {
		this.setBackground(Color.CYAN);
		
	}

}
