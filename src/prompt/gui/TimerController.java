package src.prompt.gui;

// imports
import java.util.Timer;
import java.util.TimerTask;
import src.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TimerController extends Controller {
	
	/**
	* Creates myTimer and Timer objects to work timer
	*/
	private myTimer t = new myTimer();
	private Timer timer = new Timer();
	
	@FXML
	private ComboBox<Integer> hoursCombo;
	
	@FXML
	private ComboBox<Integer> minsCombo;
	
	@FXML
	private ComboBox<Integer> secondsCombo;
	
	@FXML
	private TextField hoursText;
	
	@FXML
	private TextField minsText;
	
	@FXML
	private TextField secondsText;
	
	@FXML
	private Button startTimerBtn;
	
	@FXML
	private Button cancelTimerBtn;
	
	@FXML
	private Button resetTimerBtn;
	
	@FXML
	private Button backtoHomeBtn;
	
	/**
	* Timer task object for starting the timer
	*/
	TimerTask task = new TimerTask() {
		public void run() {
			int[] timeLeft =new int[3];
			timeLeft=t.getTimeLeft();
			hoursText.setText(String.valueOf(timeLeft[0]));
			minsText.setText(String.valueOf(timeLeft[1]));
			secondsText.setText(String.valueOf(timeLeft[2]));
			if(t.isRunning()) {
				//do nothing
			}
			else {
				task.cancel();
				timer.purge();
				hoursText.setText("0");
				minsText.setText("0");
				secondsText.setText("0");
			}
		}
	};

	/**
	* Initialize the class
	*/
	public void initializeClass() {
		hoursCombo.getItems().addAll(0,1,2,3,4,5,6,7,8,9);
		minsCombo.getItems().addAll(0,1,2,3,4, 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
				20,21,22,23,24,25,26,27,28,29,
				30,31,32,33,34,35,36,37,38,39,
				40,41,42,43,44,45,46,47,48,49,
				50,51,52,53,54,55,56,57,58,59,60
		);
		secondsCombo.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,
				20,21,22,23,24,25,26,27,28,29,
				30,31,32,33,34,35,36,37,38,39,
				40,41,42,43,44,45,46,47,48,49,
				50,51,52,53,54,55,56,57,58,59,60);
		hoursText.setEditable(false);
		minsText.setEditable(false);
		secondsText.setEditable(false);
		resetTimerBtn.setDisable(true);
		hoursCombo.requestFocus();
	}
	
	/**
	* Method to start the timer
	*/
	public void startTimer() {
		if(hoursCombo.getValue()==null || minsCombo.getValue()==null || secondsCombo.getValue()==null) {
			//pop up to show to choose a time
			errorBox("Empty Time", "Please enter a time for the timer.", AlertType.INFORMATION);
		}
		else {
			hoursText.setText(String.valueOf(hoursCombo.getValue()));
			minsText.setText(String.valueOf(minsCombo.getValue()));
			secondsText.setText(String.valueOf(secondsCombo.getValue()));
			timer.scheduleAtFixedRate(task,1000,500);
			t.start(hoursCombo.getValue(), minsCombo.getValue(), secondsCombo.getValue());
			startTimerBtn.setDisable(true);
		}
	}
	
	/**
	* Method to cancel the timer at any given time in operation
	*/
	public void cancelTimer(){
		t.cancel();
		task.cancel();
		timer.purge();
		hoursText.setText("00");
		minsText.setText("00");
		secondsText.setText("00");
		startTimerBtn.setDisable(true);
		cancelTimerBtn.setDisable(true);
	}
	
	/**
	* Reset timer timer method to create a new Timer
	*/
	public void resetTimer() {
//		Stage stage = (Stage) resetTimerBtn.getScene().getWindow();
//		stage.close();
//		sceneCtrl.activate("loginMenu");
		
		//needs work, needs to close myTimer instance and make a new one
	}
	
	/**
	* Method to return back to the user's home screen
	*/
	public void backToHome() {
		getSceneCtrl().activate("mainUI");
	}
}
