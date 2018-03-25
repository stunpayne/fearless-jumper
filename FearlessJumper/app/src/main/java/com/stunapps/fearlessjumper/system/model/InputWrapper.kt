package com.stunapps.fearlessjumper.system.model

import com.stunapps.fearlessjumper.component.input.SensorDataAdapter
import com.stunapps.fearlessjumper.system.update.InputUpdateSystem

/**
 * Created by sunny.s on 22/03/18.
 */

data class InputWrapper(var screenState: InputUpdateSystem.ScreenState, var sensorData:
SensorDataAdapter.SensorData?)
