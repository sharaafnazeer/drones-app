/*
 * Author: Sharaaf Nazeer
 * Copyright (c) 2023.
 */

package com.sharaafnazeer.dronesapp.constants;

public final class ResponseMessages {

    // Drone Related Messages
    public static final String DRONE_NOT_FOUND = "Drone not found";
    public static final String DRONE_FOUND = "Existing drone found with the same serial number";
    public static final String DRONE_CREATED = "Drone created successfully";
    public static final String DRONE_LOADED = "Drone loaded successfully";
    public static final String DRONE_NOT_CREATED = "Drone not created successfully";
    public static final String DRONE_SERIAL_EMPTY = "Please provide a serial number";
    public static final String DRONE_MODEL_EMPTY = "Please provide a model";
    public static final String DRONE_MAX_WEIGHT_EMPTY = "Please provide a maximum weight";
    public static final String DRONE_BATTERY_EMPTY = "Please provide a battery life";
    public static final String DRONE_MAX_WEIGHT_ERROR = "Drone cannot carry more than it's weight limit";
    public static final String DRONE_SERIAL_ERROR = "Drone serial number must not be less than 3 and greater than 100 characters";
    public static final String DRONE_BATTERY_ERROR = "Drone battery level should be between 0 and 100";
    public static final String DRONE_MAX_WEIGHT_EXCEEDED = "Maximum weight for drone exceeded. Please check the medication weights";
    public static final String DRONE_BATTERY_LOW = "Drone battery is very low";
    public static final String DRONE_LOADING_STATE_EXCEEDED = "Drone is not in a position to load new medications";
    public static final String FLEET_LIMIT_EXCEEDED = "Fleet limit exceeded. New drones cannot be registered";

    // Medication Related Messages

    public static final String MEDICATION_NOT_FOUND = "Medication not found";
    public static final String MEDICATION_FOUND = "Existing medication found with the same code";
    public static final String MEDICATION_CREATED = "Medication created successfully";
    public static final String MEDICATION_NOT_CREATED = "Medication not created successfully";
    public static final String MEDICATION_CODE_EMPTY = "Please provide medication code";
    public static final String MEDICATION_NAME_EMPTY = "Please provide medication name";
    public static final String MEDICATION_WEIGHT_EMPTY = "Please provide medication weight";
    public static final String MEDICATION_CODE_ERROR = "Medication code should only include upper case letters, underscore and numbers";
    public static final String MEDICATION_NAME_ERROR = "Medication name should only include letters, numbers, underscore and hyphen";
    public static final String MEDICATION_WEIGHT_ERROR = "Medication weight cannot be greater than 500 grams";


}
