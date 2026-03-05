package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
public abstract class AutoPedroCommon extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;
    ArrayList<PathItem> pathList = new ArrayList<PathItem>() ;

    public abstract void setPath();
    public void pedroDrive(Pose pose1, Pose pose2) {
        PathChain pathChain = follower.pathBuilder()
                .addPath(new BezierLine(pose1, pose2))
                .setLinearHeadingInterpolation(pose1.getHeading(), pose2.getHeading())
                .build();
        follower.followPath(pathChain,true);
    }
    public void autonomousPathUpdate() {
        if (pathState < pathList.size()) {
            if (pathState == 0) {
                pedroDrive(pathList.get(0).pose1, pathList.get(0).pose2);
                pathState = 1;
                setPathState(pathState);
            } else {
                if (!follower.isBusy()) {
                    String action = pathList.get(pathState-1).action;
                    if (isDoneAction(action)) {
                        pedroDrive(pathList.get(pathState).pose1, pathList.get(pathState).pose2);
                        pathState += 1;
                        setPathState(pathState);
                    }
                }
            }
        }
    }

    public boolean isDoneAction(String action) {
        switch (action) {
            case "shoot":
                return doneShooting();
            case "intake":
                return doneIntake();
            case "sleep_and_shoot":
                return doneSleepAndShoot();
            case "open_gate":
                return doneOpenGate();
            default:
                return true;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public boolean doneShooting() {
        if(pathTimer.getElapsedTimeSeconds() > 3) {
            pathTimer.resetTimer();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean doneIntake() {
        if(pathTimer.getElapsedTimeSeconds() > 2) {
            pathTimer.resetTimer();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean doneOpenGate() {
        if(pathTimer.getElapsedTimeSeconds() > 2) {
            pathTimer.resetTimer();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean doneSleepAndShoot() {
        if(pathTimer.getElapsedTimeSeconds() > 20) {
            telemetry.addData("In doneSleepAndShoot true", pathTimer.getElapsedTimeSeconds());
            //telemetry.update();
            pathTimer.resetTimer();
            return true;
        }
        else {
            telemetry.addData("In doneSleepAndShoot false", pathTimer.getElapsedTimeSeconds());
            //telemetry.update();
            return false;
        }
    }

    /**
     * This is the main loop of the OpMode, it will run repeatedly after clicking "Play".
     **/
    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub for debugging

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();


    }

    /**
     * This method is called once at the init of the OpMode.
     **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();


        follower = Constants.createFollower(hardwareMap);
        //buildPaths();
        setPath();
        follower.setStartingPose(pathList.get(0).pose1);

    }

    /**
     * This method is called continuously after Init while waiting for "play".
     **/
    @Override
    public void init_loop() {
    }

    /**
     * This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system
     **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /**
     * We do not use this because everything should automatically disable
     **/
    @Override
    public void stop() {
    }
}
