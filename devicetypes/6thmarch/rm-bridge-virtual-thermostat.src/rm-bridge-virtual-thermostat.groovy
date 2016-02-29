/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Bridge Virtual Thermostat
 *	Version : 1.0.2
 * 
 * 	Description:
 * 		RM Bridge Virtual Thermostat is a SmartThings Device Type that allows you to turn control splitter type 
 * 		air-conditioner by sending Infrared or RF signal from Broadlink RM device to control your home devices.
 * 		This device requires Android RM Bridge to be installed and started in an android device within the 
 * 		same wi-fi network as the Broadlink RM device.
 * 
 * 	Requirements:
 * 		An android device (Android Box/Tablet/Phone) within the same wi-fi network as the Broadlink RM device, with Android RM Bridge installed and running.
 * 		Broadlink RM device
 * 		SmartThings Hub
 * 		Amazon Echo (Only for voice control)
 * 
 * 	References:
 * 		Android RM Bridge created by Jochen Ruehl which you can get from http://rm-bridge.fun2code.de
 * 		Broadlink RM Device: http://www.broadlink.com.cn/en/home-en.html
 *		https://github.com/notoriousbdg/SmartThings.AverageThings/blob/master/AverageThings-Temperature.smartapp.groovy
 * 		https://github.com/notoriousbdg/statusbits-smartthings/blob/master/VirtualThings/VirtualTemperatureTile.device.groovy
 *		Smartthings Simulated Temperature Sensor
 *		Smartthings Simulated Thermostat
 *
 *	The original licensing applies, with the following exceptions:
 *		1.	These modifications may NOT be used without freely distributing all these modifications freely
 *			and without limitation, in source form.	 The distribution may be met with a link to source code
 *			with these modifications.
 *		2.	These modifications may NOT be used, directly or indirectly, for the purpose of any type of
 *			monetary gain.	These modifications may not be used in a larger entity which is being sold,
 *			leased, or anything other than freely given.
 *		3.	To clarify 1 and 2 above, if you use these modifications, it must be a free project, and
 *			available to anyone with "no strings attached."	 (You may require a free registration on
 *			a free website or portal in order to distribute the modifications.)
 *		4.	The above listed exceptions to the original licensing do not apply to the holder of the
 *			copyright of the original work.	 The original copyright holder can use the modifications
 *			to hopefully improve their original work.  In that event, this author transfers all claim
 *			and ownership of the modifications to "SmartThings."
 *
 *	Original Copyright information:
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *	The latest version of this file can be found at :
 *	https://github.com/6thmarch/SmartThingsPersonal
 *
 *  2015-12-26  V1.0.0  Initial release
 *	2016-02-29	V1.0.1	Renamed device type
 */

  import groovy.transform.Field
  @Field final int HIGHEST_TEMPERATURE = 31 //on() and off() need to be changed if this value is edited.


metadata {
	// Automatically generated. Make future change here.
	definition (name: "RM Bridge Virtual Thermostat", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Temperature Measurement"
		capability "Switch Level"
  		capability "Thermostat"
        capability "Switch"
        
		command "up"
		command "down"
        command "setTemperature", ["number"]
        command "parse"
	}

	preferences {
       section("RM Bridge Server Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain name or external IP Address of the Android RM Bridge Server. e.g. mydomain.com or x.x.x.x", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "text", title: "Port Number",
              description: "This is the port number of Android RM Bridge Server.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for Android RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password created for authentication for Android RM Bridge.", defaultValue: '',
              required: false, displayDuringSetup: true
              }
       
       input "onCode", "text", title: "ON",
              description: "This is the code to send to Android RM Bridge if the switch is turned on. e.g. AirConditionerOn",
              required: false, displayDuringSetup: true
              
      input "offCode", "text", title: "Off",
              description: "This is the code to send to Android RM Bridge if the switch is turned off. e.g. AirConditionerOff",
              required: false, displayDuringSetup: true
       
       16.upto(HIGHEST_TEMPERATURE, {
       		 input "tempCode${it}", "text", title: "ON Code ${it}",
              description: "This is the code to send to Android RM Bridge to adjust the temperature. e.g. AirCon18",
              required: false, displayDuringSetup: true
    
			})
       
    }

	// UI tile definitions
	tiles {
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}°C', unit:"dC",
				backgroundColors:[
					[value: 16, color: "#153591"],
					[value: 18, color: "#1e9cbb"],
					[value: 20, color: "#90d2a7"],
					[value: 22, color: "#44b621"],
					[value: 24, color: "#f1d801"],
					[value: 26, color: "#d04e00"],
					[value: 28, color: "#bc2323"]
				]
			)
		}
        
        valueTile("coolingSetpoint", "device.coolingSetpoint", inactiveLabel: false, decoration: "flat") {
			state "cool", label:'${currentValue}°C cool', unit:"C", backgroundColor:"#ffffff"
		}
        standardTile("mode", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "off", label:'${name}', action:"thermostat.on", backgroundColor:"#ffffff"
			state "on", label:'${name}', action:"thermostat.off", backgroundColor:"#269bd2"
            state "idle", label:'${name}', action:"thermostat.on", backgroundColor:"#269bd2"
			//state "auto", label:'${name}', action:"thermostat.off", backgroundColor:"#79b821"
		}
		standardTile("up", "device.temperature", inactiveLabel: false, decoration: "flat") {
			state "default", label:'up', action:"up"
		}        
		standardTile("down", "device.temperature", inactiveLabel: false, decoration: "flat") {
			state "default", label:'down', action:"down"
		}
        main "temperature"
		details("temperature","coolingSetpoint","mode","up","down")
	}
}

// Parse incoming device messages to generate events
def parse(String description) {
	log.debug "Parse description: $description"
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim(), unit:"dC")
   
	Map msg = stringToMap(description)
    if(msg.containsKey("temperature"))
    {
       	Float temp = msg.temperature.toFloat()
   	    sendEvent(name: "temperature", value: temp.round(1))
         evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"))
    }


}


def evaluate(temp,newCoolingSetpoint, newState) {
	log.debug "evaluate($temp, $newCoolingSetpoint)"
	def threshold = 1.0
	def current = device.currentValue("thermostatOperatingState")
	def mode = newState

	def cooling = false
	def idle = false
	log.debug "evaluate in thermostat"
	if (mode in ["cool","auto", "on", "idle"]) {
		if (temp - newCoolingSetpoint >= 0) {
        	if(newCoolingSetpoint != device.currentValue("coolingSetpoint") | device.currentValue("switch") != "on"){
                makeJSONBroadlinkRMBridgeRequest(getSetTempCode(newCoolingSetpoint))
             	sendEvent(name: "switch", value: "on")
                sendEvent(name: "coolingSetpoint", value: newCoolingSetpoint)

            }
      		cooling = true
            sendEvent(name: "thermostatOperatingState", value: "cooling")




		}
		else if (newCoolingSetpoint - temp >= threshold) {
            sendEvent(name: "coolingSetpoint", value: newCoolingSetpoint)
            idle = true
            
		}
		sendEvent(name: "thermostatSetpoint", value: coolingSetpoint)
	}
    else if(mode == "off"){
            if(current != "off"){
                makeJSONBroadlinkRMBridgeRequest("$offCode")
                sendEvent(name: "thermostatOperatingState", value: "off")
                                sendEvent(name: "switch", value: "off")


            }
    	   
    }
	if (idle && !cooling && mode != "idle") {
            if(current != "idle"){
                makeJSONBroadlinkRMBridgeRequest("$offCode")
                sendEvent(name: "thermostatOperatingState", value: "idle")
                sendEvent(name: "switch", value: "idle")
                
            }
		
	}
}

def setLevel(value) {
	log.debug "setLevel()"
	sendEvent(name:"temperature", value: value)
     evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"), "on")
}

def up() {
	log.debug "up()"

	def ts = device.currentState("coolingSetpoint")
	def degreesC = ts ? ts.integerValue + 1 : 24 
//	sendEvent(name:"coolingSetpoint", value: degreesC)
   	evaluate(device.currentValue("temperature"), degreesC, "on")
}

def down() {
	log.debug "down()"

	def ts = device.currentState("coolingSetpoint")
	def degreesC = ts ? ts.integerValue - 1 : 24 
//	sendEvent(name:"coolingSetpoint", value: degreesC)
  	evaluate(device.currentValue("temperature"), degreesC, "on")
}

def setTemperature(value) {
	sendEvent(name:"temperature", value: value)
    evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"), "on")


}

def setCoolingSetpoint(Double degreesC) {
	log.debug "setCoolingSetpoint($degreesC)"
//	sendEvent(name: "coolingSetpoint", value: degreesC)
	evaluate(device.currentValue("temperature"), degreesC, "on")


}

def off() {
	log.debug "off()"	
	//sendEvent(name: "switch", value: "off")
	evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"), "off")


}

//def auto() {
//	sendEvent(name: "switch", value: "auto")
//	evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"))
//}

def on() {
	log.debug "on()"
	//sendEvent(name: "switch", value: "on")
	evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"), "on")
}

def cool() {
	log.debug "cool()"
	sendEvent(name: "switch", value: "cool")
	evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"), "on")
}

def getSetTempCode(value){
	if(value <= 16){
    	return "${tempCode16}"
    }
    else if(value == 17){
	   	return "${tempCode17}"
    }
    else if(value == 18){
	   	return "${tempCode18}"
    }
    else if(value == 19){
	   	return "${tempCode19}"
    }
    else if(value == 20){
	   	return "${tempCode20}"
    }
    else if(value == 21){
	   	return "${tempCode21}"
    }
    else if(value == 22){
	   	return "${tempCode22}"
    }
    else if(value == 23){
	   	return "${tempCode23}"
    }
    else if(value == 24){
	   	return "${tempCode24}"
    }
    else if(value == 25){
	   	return "${tempCode25}"
    }
    else if(value == 26){
	   	return "${tempCode26}"
    }
    else if(value == 27){
	   	return "${tempCode27}"
    }
    else if(value == 28){
	   	return "${tempCode28}"
    }
    else if(value == 29){
	   	return "${tempCode29}"
    }
    else if(value == 30){
	   	return "${tempCode30}"
    }
    else if(value >= 31){
	   	return "${tempCode31}"
    }
}

//Send code to RM Bridge Server to trigger sending of IR/RF signal from Broadlink RM device.
def makeJSONBroadlinkRMBridgeRequest(String code) {
    log.debug "Sending code: ${code}"
    def params = [
        uri:  "http://$username:$passwd@$server:$port/code/",
        path: "$code",
        contentType: 'application/json'        
    ]
    try {
        httpGet(params) {resp ->
            log.debug "resp data: ${resp.data}"
            log.debug "code: ${resp.data.code}"
            log.debug "msg: ${resp.data.msg}"
        }
    } catch (e) {
        log.error "error: $e"

    }
}