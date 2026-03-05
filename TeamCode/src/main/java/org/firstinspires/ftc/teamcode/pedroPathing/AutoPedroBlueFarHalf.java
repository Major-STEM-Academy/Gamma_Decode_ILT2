package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "PedroPathing Auto Blue Far Half", group = "Examples")
public class AutoPedroBlueFarHalf extends AutoPedroCommon {
    private final Pose blue_far_init = new Pose(20, 122, Math.toRadians(140)); // Start Pose of our robot.
    private final Pose blue_far_score = new Pose(48, 82, Math.toRadians(136)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    private final Pose blue_1st_row_start = new Pose(50, 78, Math.toRadians(180)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose blue_1st_row_end = new Pose(25, 78, Math.toRadians(180)); // Middle (Second Set) of Artifacts from the Spike Mark.
    private final Pose blue_2nd_row_start = new Pose(50, 54, Math.toRadians(180)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose blue_2nd_row_end = new Pose(25, 54, Math.toRadians(180)); // Middle (Second Set) of Artifacts from the Spike Mark.
    private final Pose blue_gate = new Pose(18, 60, Math.toRadians(180));
    private final Pose blue_3rd_row_start = new Pose(50, 30, Math.toRadians(180)); // Highest (First Set) of Artifacts from the Spike Mark.
    private final Pose blue_3rd_row_end = new Pose(25, 30, Math.toRadians(180)); // Middle (Second Set) of Artifacts from the Spike Mark.

    private final Pose blue_parking = new Pose(41, 38, Math.toRadians(0)); //
    public void setPath(){
        pathList.add(new PathItem(blue_far_init,blue_far_score,"shoot","shoot_preload"));
        pathList.add(new PathItem(blue_far_score,blue_2nd_row_start,"none","go_to_2nd_row"));
        pathList.add(new PathItem(blue_2nd_row_start,blue_2nd_row_end,"intake","intake_2nd_row"));
        pathList.add(new PathItem(blue_2nd_row_end,blue_gate,"open_gate","shoot_2nd_row"));
        pathList.add(new PathItem(blue_gate,blue_far_score,"shoot","shoot_2nd_row"));

        pathList.add(new PathItem(blue_far_score,blue_1st_row_start,"none","go_to_1st_row"));
        pathList.add(new PathItem(blue_1st_row_start,blue_1st_row_end,"intake","intake_1st_row"));
        pathList.add(new PathItem(blue_1st_row_end,blue_far_score,"shoot","shoot_1st_row"));

        /*
        pathList.add(new PathItem(blue_far_score,blue_3rd_row_start,"none","go_to_3rd_row"));
        pathList.add(new PathItem(blue_3rd_row_start,blue_3rd_row_end,"intake","intake_3rd_row"));
        pathList.add(new PathItem(blue_3rd_row_end,blue_far_score,"shoot","shoot_3rd_row"));
        pathList.add(new PathItem(blue_far_score,blue_parking,"none","parking"));

         */
    }
}
