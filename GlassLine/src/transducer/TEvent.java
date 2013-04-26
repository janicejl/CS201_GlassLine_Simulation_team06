
package transducer;

/**
 * Events that the transducer can fire
 */
public enum TEvent
{
	// global events
	START,
	STOP,
	SET_RECIPE,

	// conveyor
	CONVEYOR_DO_START,
	CONVEYOR_DO_STOP,

	// sensor
	SENSOR_GUI_PRESSED,
	SENSOR_GUI_RELEASED,

	// popup
	POPUP_DO_MOVE_UP,
	POPUP_GUI_MOVED_UP,
	POPUP_DO_MOVE_DOWN,
	POPUP_GUI_MOVED_DOWN,
	POPUP_RELEASE_GLASS,
	POPUP_GUI_LOAD_FINISHED,
	POPUP_GUI_RELEASE_FINISHED,

	// truck
	TRUCK_DO_LOAD_GLASS,
	TRUCK_GUI_LOAD_FINISHED,
	TRUCK_DO_EMPTY,
	TRUCK_GUI_EMPTY_FINISHED,

	// workstations
	WORKSTATION_DO_ACTION,
	WORKSTATION_RELEASE_GLASS,
	WORKSTATION_GUI_ACTION_FINISHED,
	WORKSTATION_DO_LOAD_GLASS,
	WORKSTATION_LOAD_FINISHED,
	WORKSTATION_RELEASE_FINISHED,
	
	//bin and "Glass Ghosts"
	BIN_CREATE_PART,
	BIN_PART_CREATED,
	
	//V2
	POPUP_JAM,
	POPUP_UNJAM,
	WORKSTATION_DISABLE_OFFLINE,
	WORKSTATION_ENABLE_OFFLINE,
	WORKSTATION_OFFLINE_CHANGE_SPEED,
	GLASS_BREAK_OFFLINE,
	ROMOVE_GLASS_OFFLINE,
	
	//v2 online workstation
	WORKSTATION_DISABLE_ONLINE,
	WORKSTATION_ENABLE_ONLINE,
	
	//v2 jam conveyors
	CONVEYOR_BREAK,
	CONVEYOR_FIX,
}
