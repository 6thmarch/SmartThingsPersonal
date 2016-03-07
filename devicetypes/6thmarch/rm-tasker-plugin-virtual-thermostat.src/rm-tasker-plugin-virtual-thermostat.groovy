/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin Virtual Thermostat 
 *	Version : 1.0.1
 * 
 * 	Description:
 * 		RM Tasker Plugin Virtual Thermostat is a SmartThings Device Type that act as a virtual thermostat and controls a air conditioner.
 * 		This device requires RM Tasker Plugin to be installed and started in an android device within the 
 * 		same wi-fi network as the Broadlink RM device.
 * 
 * 	Requirements:
 * 		An android device (Android Box/Tablet/Phone) within the same wi-fi network as the Broadlink RM device, with RM Tasker Plugin HTTP Bridge installed and running.
 * 		Broadlink RM device
 * 		SmartThings Hub
 * 		Amazon Echo (Only for voice control)
 * 
 * 	References:
 * 		RM Tasker Plugin HTTP Bridge: https://play.google.com/store/apps/details?id=us.originally.tasker&hl=en
 * 		Broadlink RM Device: http://www.broadlink.com.cn/en/home-en.html
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
 *  2016-02-29  V1.0.0  Initial release
 *	2016-03-08	V1.0.1	Switch from HTTP GET request to HTTP POST request
 */

  import groovy.transform.Field
  @Field final int HIGHEST_TEMPERATURE = 28 //on() and off() need to be changed if this value is edited.


metadata {
	// Automatically generated. Make future change here.
	definition (name: "RM Tasker Plugin Virtual Thermostat", namespace: "6thmarch", author: "Benjamin Yam") {
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
       section("RM Tasker Plugin HTTP Bridge Configuration"){
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
       input "deviceMacId", "text", title: "Device Mac ID",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
       
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
			state "off", label:'${name}', action:"on", backgroundColor:"#ffffff"
			state "on", label:'${name}', action:"thermostat.off", backgroundColor:"#269bd2"
            state "idle", label:'${name}', action:"thermostat.off", backgroundColor:"#269bd2"
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
   	    sendEvent(name: "temperature", value: temp.trunc())
         evaluate(device.currentValue("temperature"), device.currentValue("coolingSetpoint"))
    }


}


def evaluate(temp,newCoolingSetpoint, newState) {
	log.debug "evaluate($temp, $newCoolingSetpoint)"
	def threshold = 1.0
	def current = device.currentValue("thermostatOperatingState")
	def mode = newState
    newCoolingSetpoint = newCoolingSetpoint.round()

	def cooling = false
	def idle = false
	log.debug "onTemp${newCoolingSetpoint}"
	if (mode in ["cool","auto", "on", "idle"]) {
		if (temp - newCoolingSetpoint >= 0) {
        	if(newCoolingSetpoint != device.currentValue("coolingSetpoint") | device.currentValue("switch") != "on"){
                api("onTemp${newCoolingSetpoint}", [], {})
             	sendEvent(name: "switch", value: "on")
                sendEvent(name: "coolingSetpoint", value: newCoolingSetpoint)

            }
      		cooling = true
            sendEvent(name: "thermostatOperatingState", value: "cooling")

		}
		else if (newCoolingSetpoint - temp >= 0) {
            sendEvent(name: "coolingSetpoint", value: newCoolingSetpoint)
            idle = true
            
		}
		sendEvent(name: "thermostatSetpoint", value: coolingSetpoint)
	}
    else if(mode == "off"){
            if(current != "off"){
              	api('powerOff', [], {})
                sendEvent(name: "thermostatOperatingState", value: "off")
                                sendEvent(name: "switch", value: "off")


            }
    	   
    }
	if (idle && !cooling && mode != "idle") {
            if(current != "idle"){
              	api('powerOff', [], {})
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


def api(method, args = [], success = {}) {

def methods = [
'powerOn': [code: onCode, type: 'post'],
'powerOff': [code: offCode, type: 'post'],
'onTemp16': [code: tempCode16, type: 'post'],
'onTemp17': [code: tempCode17, type: 'post'],
'onTemp18': [code: tempCode18, type: 'post'],
'onTemp19': [code: tempCode19, type: 'post'],
'onTemp20': [code: tempCode20, type: 'post'],
'onTemp21': [code: tempCode21, type: 'post'],
'onTemp22': [code: tempCode22, type: 'post'],
'onTemp23': [code: tempCode23, type: 'post'],
'onTemp24': [code: tempCode24, type: 'post'],
'onTemp25': [code: tempCode25, type: 'post'],
'onTemp26': [code: tempCode26, type: 'post'],
'onTemp27': [code: tempCode27, type: 'post'],
'onTemp28': [code: tempCode28, type: 'post'],
'onTemp29': [code: tempCode29, type: 'post'],
'onTemp30': [code: tempCode30, type: 'post'],
'onTemp31': [code: tempCode31, type: 'post'],
    ]
def request = methods.getAt(method)
    doRequest(request.code, args, request.type, success)
}
def doRequest(code, args, type, success) {
    log.debug "Calling $type : $code : $args"
    def repeatVal = 1
    if(args['repeat']){
    	repeatVal = args['repeat']
    }
    log.debug "repeatVal: $repeatVal"
def params = [
uri: "http://$server:$port",
path: "/send",
headers: [
'Accept': "application/json"
        ],
query: ['deviceMac' : deviceMacId, 'codeId' : code, 'repeat': repeatVal] //args 
    ]
	if(type == 'post') {
       httpPostJson(params, success)
       log.debug success
    } else if (type == 'get') {
       httpGet(params, success)
       log.debug success
    } else if (type == 'put') {
    	httpPutJson(params, success)
        log.debug success
    }
}


