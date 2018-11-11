package org.usfirst.frc.team4141.MDRobotBase;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.HALUtil;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team4141.MDRobotBase.config.BooleanConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigPreferenceManager;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.eventmanager.LogNotification.Level;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotLogNotification;
import org.usfirst.frc.team4141.MDRobotBase.notifications.RobotNotification;
import org.usfirst.frc.team4141.MDRobotBase.sensors.RobotDiagnostics;
import org.usfirst.frc.team4141.MDRobotBase.sensors.Sensor;
import org.usfirst.frc.team4141.MDRobotBase.sensors.SensorReading;
import org.usfirst.frc.team4141.robot.OI;
import org.usfirst.frc.team4141.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team4141.robot.subsystems.DiagnosticsSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.WebSocketSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public abstract class MDRobotBase extends IterativeRobot{

	static {
		Properties props = System.getProperties();
		props.setProperty("java.net.preferIPv4Stack", "true");
	}
	private OI oi;
	private Hashtable<String,MDSubsystem> subsystems;
	private Hashtable<String,MDCommandGroup> commandChooser;

	private String name;
	
	private Hashtable<String,SensorReading> sensorReadingsDictionary;
	private Hashtable<String,Sensor> sensorsDictionary;

	//function prototype that implementing robot must implement
    protected abstract void configureRobot();
    
    //Constructors
	public MDRobotBase() {
		this(null);
	}
	public MDRobotBase(String name) {
		
		this.name = name;
		sensorReadingsDictionary = new Hashtable<String,SensorReading>();
		sensorsDictionary = new Hashtable<String,Sensor>();
	}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	
	//register subsystems
    public void add(MDSubsystem subsystem){
    	// debug("adding subsystem "+subsystem.getName());
    	this.subsystems.put(subsystem.getName(),subsystem);
    }
    
    // Return hashtable of all subsystems
    // This method should not be used externally; instead call one of the following methods: 
    //    getSubsystem() to look up a subsystem by name and return it
    //    containsSubsystem() to determine whether a subsystem of a given name exists
    //    getAllSubsystemNames() to get an array of all subsystem names
	private Hashtable<String, MDSubsystem> getSubsystems() {
		return subsystems;
	}
	
	// Return an array of the names of all subsystems - i.e. the keys in the hashtable
	public Object[] getAllSubsystemNames() {
		return getSubsystems().keySet().toArray();
	}
	
	// Look up one subsystem in hashtable and return it
	public MDSubsystem getSubsystem(String subsystemName){
		
		if (getSubsystems()==null) {
			// If the hashtable does not exist, there are no subsystems defined so raise an error
			log(Level.ERROR, "getSubsystem()", "Subsystem " + subsystemName + " not found because Hashtable does not exist");
			throw new IllegalArgumentException("Subsystem " + subsystemName + " not found because Hashtable does not exist");
		}
		
		if(!getSubsystems().containsKey(subsystemName)){
			// This subsystem name is not in the hastable so print error and contents of hashtable, then quit
			log(Level.ERROR, "getSubsystem()", "Subsystem name not found");
			
			// Print contents of hashtable
			String errorString;
			errorString = "!!! Could not find a subsystem by the name of " + subsystemName;
			
			if (subsystems != null) {
				// get set of subsystem keys
		        Set<String> setOfSubsystemKeys = subsystems.keySet();
		        
				errorString += "\nNumber of subsystems = " + setOfSubsystemKeys.size() + ":\n";
				
		        // Loop over keys
		        for (String key : setOfSubsystemKeys) {
		    		// Print Subsystem
		    		errorString += key + "   ";
		        }
			}
			else {
				errorString += "Warning: subsystems hashtable not defined!";
			}
			log(Level.ERROR, "getSubsystem", errorString);
			System.out.println(errorString);
			
			throw new IllegalArgumentException("Subsystem " + subsystemName + " not found");
		}
		
		return getSubsystems().get(subsystemName);
	}
	
	// Check to see if a subsystem with the given name is in the hashtable and return True/False
	public boolean containsSubsystem(String subsystemName){
		
		// If the hashtable does not exist, there are no subsystems defined
		if (getSubsystems()==null) return false;
		
		if(!getSubsystems().containsKey(subsystemName)) return false;
		
		else return true;
	}
	
	//register sensors

    public Hashtable<String, SensorReading> getSensorReadingsDictionary() {
		return sensorReadingsDictionary;
	}	
    public Hashtable<String, Sensor> getSensorsDictionary() {
		return sensorsDictionary;
	}	
    
    public Hashtable<String, MDCommandGroup> getCommandChooser() {
		return commandChooser;
	}
	
	
	//Log helper methods
	public void log(String logOrigin, String message) {
		log(Level.INFO,logOrigin, message);		
	}

	public void log(Level level, String logOrigin, String message, boolean showInConsole, String target, boolean record) {
		post(new RobotLogNotification(level, logOrigin, message, showInConsole, null, record));
	}	
	
	public void log(Level level, String logOrigin, String message, String target) {
		switch(level){
		case DEBUG:
			log(level,logOrigin,message,true,target,false);
			break;
		case ERROR:
			log(level,logOrigin,message,true,target,true);
			break;
		default:
			log(level,logOrigin,message,false,target,false);
		}
	}
	
	public void log(Level level, String logOrigin, String message) {
		switch(level){
		case DEBUG:
			log(level,logOrigin,message,true,null,false);
			break;
		case ERROR:
			log(level,logOrigin,message,true,WebSocketSubsystem.Remote.console.toString(),true);
			break;
		default:
			log(level,logOrigin,message,false,WebSocketSubsystem.Remote.console.toString(),false);
		}
	}

//	public Logger getLogger() {
//		return logger;
//	}

    protected MDCommandGroup autonomousCommand=null;
	
    // Operator Interface
    public OI getOi() {
		return oi;
	}

    
	//Robot Licycle Methods.  These are called by the FRC system
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
    	
    	this.subsystems=new Hashtable<String,MDSubsystem>();
    	this.commandChooser=new Hashtable<String,MDCommandGroup>();
    	oi = new OI(this);
    	
    	if(HALUtil.getFPGAButton()){
    		debug("resetting preferences");
        	ConfigPreferenceManager.clearPreferences();
    	}
    	// *** pre configured subsystems
		
		//Special Subsystem used for RobotDiagnostics
		add( new DiagnosticsSubsystem(this, "diagnosticsSubsystem")
				 .add("diagnosticsSensor",new RobotDiagnostics())
				 .add("diagnosticsScanPeriod",new DoubleConfigSetting(0.05, 1.0, 0.1))
				 .configure()
		);
		
		//Subsystem to manage WebSocket Communications
		add( new WebSocketSubsystem(this, "WebSockets")
				 .add("enableWebSockets",new BooleanConfigSetting(true))
				 .configure()
		);    	
		
		//--------------Camera------------------------------------------------------//
		 new Thread(() -> {
             UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
             camera.setResolution(320, 240);
             
             CvSink cvSink = CameraServer.getInstance().getVideo();
             CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 320, 240);
             
             Mat source = new Mat();
             Mat output = new Mat();
             
             while(!Thread.interrupted()) {
                 cvSink.grabFrame(source);
                 Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
                 outputStream.putFrame(output);
             }
         }).start();
		 
		 //---------------------------------------------------------------------------//
		debug("about to call configure robot");
		System.out.println("print ln about to call configure robot");
    	configureRobot();  
    	oi.configureOI();
    	debug("RobotInit completed");
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
	public void disabledInit(){
//    	System.out.println("disabledInit()");
    }
    
    /**
     * This function is called periodically while the robot is in disabled state
     */
    @Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}    
	
    /**
     * This function is called once right before the robot goes into autonomous mode.
     */    
    @Override
	public void autonomousInit() {
    	if (autonomousCommand != null){
    		debug("autonomous command should start");
    		autonomousCommand.start();
    	}
    	else{
    		debug("autonomousCommand is unexpectedly null");
    	}
    }

    /**
     * This function is called periodically during autonomous mode
     */
    @Override
   	public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }  

    
    /**
     * This function is called once right before the robot goes into teleop mode.
     */    
    @Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    	ArcadeDriveCommand arcadeCommand = new ArcadeDriveCommand(this);
    	arcadeCommand.start();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }   

    /**
     * This function is called once right before the robot goes into test mode.
     */  	@Override
	public void testInit() {
	}
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
//    	  No longer required
//        LiveWindow.run();
    }

	public void add(SensorReading reading) {
		if(sensorReadingsDictionary!=null){
			sensorReadingsDictionary.put(reading.getName(), reading);
			System.out.println("registering sensor reading: "+reading.getName());
		}
	}
	
	
	public void add(Sensor sensor) {
		if(sensorsDictionary!=null){
				sensorsDictionary.put(sensor.getName(), sensor);
		}
	}
	public void post(RobotNotification notification){
		if(getSubsystems()!=null && getSubsystems().containsKey("WebSockets")){
			((WebSocketSubsystem)(getSubsystems().get("WebSockets"))).post(notification);
		}
	}
	
	protected void setAutonomousCommand(MDCommandGroup[] commands) {
		setAutonomousCommand(commands,null);
		for(MDCommandGroup command : commands){
			commandChooser.put(command.getName(), command);
			if(autonomousCommand==null){
				debug("defaulting autoCommand to "+command.getName());
				autonomousCommand = command;
			}
		}
	}

	protected void setAutonomousCommand(MDCommandGroup[] commands, String defaultCommandName) {
		if(commands==null || commands.length<1){
			debug("no automous commands to set");
			return;
		}
		autonomousCommand = null;
		for(MDCommandGroup command : commands){
			commandChooser.put(command.getName(), command);
			if(defaultCommandName!=null && defaultCommandName.equals(command.getName())){
				debug("defaulting autoCommand to "+command.getName());
				autonomousCommand = command;
			}
		}
		if(autonomousCommand==null){
			debug("defaulting autoCommand to "+commands[0].getName());
			autonomousCommand = commands[0];
		}
	}
	
	protected void setAutonomousCommand(MDCommandGroup command) {
		setAutonomousCommand(new MDCommandGroup[]{command});
	}
	
	public void setAutoCommand(String commandName) {
		if(commandChooser.containsKey(commandName)){
			autonomousCommand = commandChooser.get(commandName);
		}
	}

	public void debug(String message) {
		log(Level.DEBUG, "", message);
//		System.out.println("Debug; " + message);
	}

	public MDCommandGroup getAutoCommand() {
		return autonomousCommand;
	}
	@Override
	public void robotPeriodic() {
	}

	public boolean hasAutoCommand(String name) {
		return commandChooser.containsKey(name);
	}	

	public String toString() {
		String objectString;
		objectString = "\n===========================================";
		objectString += "\nRobot class = " + this.getClass().getName();
		objectString += "\nRobot name = "  + this.name;
		objectString += "\nEnabled = " + this.isEnabled();
		objectString += "\nDisabled = " + this.isDisabled();
		objectString += "\nAutonomous = " + this.isAutonomous();
		objectString += "\nTest = " + this.isTest();
		objectString += "\nOperatorControl = " + this.isOperatorControl();

		
		if (subsystems != null) {
			// get set of subsystem keys
	        Set<String> setOfSubsystemKeys = subsystems.keySet();
	        
			objectString += "\nNumber of subsystems = " + setOfSubsystemKeys.size();
			int i = 1;
			
	        // Loop over keys
	        for (String key : setOfSubsystemKeys) {
	    		objectString += "\nSubsystem #" + i;
	    		// Print Subsystem
	    		objectString += subsystems.get(key).toString();
	    		++i;
	        }
	        objectString += "\nEnd of Subsystems";
	        objectString += "\n-----------------";
		}
		else {
			objectString += "Warning: subsystems hashtable not defined!";
		}
		
		if (commandChooser != null) {
			// get set of commandChooser keys
	        Set<String> setOfCommandChooserKeys = commandChooser.keySet();
	        
			objectString += "\nNumber of Command Groups in Command Chooser = " + setOfCommandChooserKeys.size();
			int i = 1;
			
	        // Loop over keys
	        for (String key : setOfCommandChooserKeys) {
	    		objectString += "\nCommand Group #" + i;
	    		// Print Command Group Name
	    		objectString += commandChooser.get(key).toString();
	    		++i;
	        }
	        objectString += "\nEnd of Command Groups";
	        objectString += "\n-----------------";
		}
		else {
			objectString += "Warning: comanndChooser hashtable not defined!";
		}
		
		return objectString;
	}

}



