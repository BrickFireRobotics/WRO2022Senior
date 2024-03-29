package team.brickfire.actions;

import lejos.utility.Delay;
import team.brickfire.data.color.AdvancedColor;
import team.brickfire.data.color.LaundryBlockColorMap;
import team.brickfire.robot_parts.arms.BlockArm;
import team.brickfire.robot_parts.arms.WaterBottleArm;

/**
 * <p>Action for everything to do with Water Bottles</p>
 *
 * @version 2.0
 * @author Team BrickFire
 */
public final class WaterBottleAction extends BaseAction {

    private static WaterBottleAction instance;
    private final boolean[] bottles;

    private WaterBottleAction() {
        super();
        this.bottles = new boolean[]{true, true};
    }

    /**
     * <p>Returns the instance of this class</p>
     * <p><i>Singleton-Pattern</i></p>
     * @return The instance of WaterBottleAction
     */
    public static WaterBottleAction getInstance() {
        if (instance == null) {
            instance = new WaterBottleAction();
        }
        return instance;
    }

    /**
     * <p>Delivers a water bottle and leaves the room</p>
     * @param thingsOnLeft Whether the table is on the left side of the room
     */
    public void deliverBottle(boolean thingsOnLeft) {
        boolean driveDeep = thingsOnLeft && !bottles[1] || !thingsOnLeft && !bottles[0];
        bottles[thingsOnLeft && bottles[1] || !thingsOnLeft && !bottles[0] ? 1 : 0] = false;

        // drive into room
        setDrivingSpeed(80, 150);
        drive(5.5);
        blockArm.move(BlockArm.NUDGE);
        drive(driveDeep ? 16 : 7);
        AdvancedColor c = new AdvancedColor(colorSensorBlocks, new LaundryBlockColorMap());
        blockArm.move(BlockArm.HIGHEST, true);
        Delay.msDelay(200);
        LaundryAction.getInstance().enterScan(c);

        // drive to table
        turn(thingsOnLeft ? 91 : -90);
        blockArm.move(BlockArm.MIDDLE, true);
        setDrivingSpeed(100, 200);
        drive(-20);

        // Drop off bottle
        blockArm.move(BlockArm.LOWEST, true);
        waterBottleArm.move(WaterBottleArm.TABLE);
        drive(10);
        if (bottles[0] || bottles[1]) {
            waterBottleArm.move(WaterBottleArm.OVER_TABLE);
        } else {
            waterBottleArm.move(WaterBottleArm.START, true);
        }

        // Leave room
        drive(11.8);
        setTurningSpeed(80, 150);
        turn(thingsOnLeft ? 87 : -86);
        blockArm.move(BlockArm.MIDDLE, true);
        setDrivingSpeed(100, 150);
        drive(driveDeep ? 23.5 : 17);
    }
}
