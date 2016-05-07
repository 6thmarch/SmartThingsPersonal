/**
 *  Copyright 2016 Benjamin Yam
 *	
 *	RM Tasker Plugin 3 Speed Fan Controller 
 *	Version : 1.0.0
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
 *		Modified from https://github.com/ChadCK/SmartThings/blob/master/Device-Types/Z-Wave_Smart_Fan_Control.groovy
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
 *  2016-05-07  V1.0.0  Initial release
 */


metadata {
	definition (name: "RM Tasker Plugin 3 Speed Fan Controller", namespace: "6thmarch", author: "Benjamin Yam") {
		capability "Switch Level"
		capability "Actuator"
		capability "Indicator"
		capability "Switch"
		capability "Polling"
		capability "Refresh"
		capability "Sensor"

		command "lowSpeed"
		command "medSpeed"
		command "highSpeed"

		attribute "currentState", "string"

	}

	tiles (scale:2) {
		multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true) {
			tileAttribute ("device.currentState", key: "PRIMARY_CONTROL") {
  				attributeState "default", label:'ADJUSTING', action:"refresh.refresh", icon:"st.Lighting.light24", backgroundColor:"#2179b8", nextState: "turningOff"
				attributeState "HIGH", label:'HIGH', action:"switch.off", icon:"st.Lighting.light24", backgroundColor:"#486e13", nextState: "OFF"
				attributeState "MED", label:'MED', action:"switch.off", icon:"st.Lighting.light24", backgroundColor:"#60931a", nextState: "OFF"
				attributeState "LOW", label:'LOW', action:"switch.off", icon:"st.Lighting.light24", backgroundColor:"#79b821", nextState: "OFF"
				attributeState "OFF", label:'OFF', action:"switch.on", icon:"st.Lighting.light24", backgroundColor:"#ffffff", nextState: "default"
			}
			tileAttribute ("device.level", key: "SECONDARY_CONTROL") {
				attributeState "level", label:'${currentValue}%'
			}
		}
		standardTile("lowSpeed", "device.currentState", inactiveLabel: false, width: 2, height: 2) {
        	state "default", label: 'LOW', action: "lowSpeed", icon:"st.Home.home30", backgroundColor: "#ffffff"
			state "LOW", label:'LOW', action: "lowSpeed", icon:"st.Home.home30", backgroundColor: "#79b821"
  		}
		standardTile("medSpeed", "device.currentState", inactiveLabel: false, width: 2, height: 2) {
			state "default", label: 'MED', action: "medSpeed", icon:"st.Home.home30", backgroundColor: "#ffffff"
			state "MED", label: 'MED', action: "medSpeed", icon:"st.Home.home30", backgroundColor: "#79b821"
		}
		standardTile("highSpeed", "device.currentState", inactiveLabel: false, width: 2, height: 2) {
			state "default", label: 'HIGH', action: "highSpeed", icon:"st.Home.home30", backgroundColor: "#ffffff"
			state "HIGH", label: 'HIGH', action: "highSpeed", icon:"st.Home.home30", backgroundColor: "#79b821"
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
		}
		standardTile("indicator", "device.indicatorStatus", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "when off", action:"indicator.indicatorWhenOn", icon:"st.indicators.lit-when-off"
			state "when on", action:"indicator.indicatorNever", icon:"st.indicators.lit-when-on"
			state "never", action:"indicator.indicatorWhenOff", icon:"st.indicators.never-lit"
		}
		controlTile("levelSliderControl", "device.level", "slider", height: 2, width: 2, inactiveLabel: false) {
			state "level", action:"switch level.setLevel"
		}
		main(["switch"])
		details(["switch", "lowSpeed", "medSpeed", "highSpeed", "indicator", "levelSliderControl", "refresh"])
	}
	preferences {
		section("Fan Thresholds") {
			input "lowThreshold", "number", title: "Low Threshold", range: "1..99"
			input "medThreshold", "number", title: "Medium Threshold", range: "1..99"
			input "highThreshold", "number", title: "High Threshold", range: "1..99"
		}
        section("RM Tasker Plugin HTTP Bridge Configuration"){
       input "server", "text", title: "Server Address",
              description: "This is the domain name or external IP Address of the HTTP Bridge. e.g. mydomain.com or x.x.x.x", defaultValue: '',
              required: true, displayDuringSetup: true

       input "port", "number", title: "Port Number",
              description: "This is the port number of HTTP Bridge.", defaultValue: '',
              required: true, displayDuringSetup: true
              
       input "username", "text", title: "Username",
              description: "This is the username for authentication for HTTP Bridge. (Not available in RM Tasker Plugin yet))", defaultValue: '',
              required: false, displayDuringSetup: true
              
       input "passwd", "password", title: "Password",
              description: "This is the password created for authentication for HTTP Bridge. (Not available in RM Tasker Plugin yet)", defaultValue: '',
              required: false, displayDuringSetup: true
              }
       input "deviceMacId", "text", title: "Device Mac ID",
              description: "This is the device MAC ID of the RM device to send the code. e.g. xx:xx:xx:xx:xx:xx",
              required: true, displayDuringSetup: true
                 
               input "fanOnCode", "number", title: "Fan On Code",
              description: "This is the code to send to HTTP Bridge to on the fan.",
               required: true, displayDuringSetup: true
              
              input "fanOffCode", "number", title: "Fan Off Code",
              description: "This is the code to send to HTTP Bridge to off the fan.",
               required: true, displayDuringSetup: true
              
              input "fanSpeedChangeCode", "number", title: "Fan Speed Change Code",
              description: "This is the code to send to HTTP Bridge to change the speed of the fan.",
               required: false, displayDuringSetup: true
	
     
	}
}

def parse(String description) {
		log.debug "Parsing '${description}'"

}

def on() {
	log.info "on"
    
	api('powerOn', [], {
        sendEvent(name: 'currentState', value: state.lastSpeedState)
    })
    }

def off() {
	log.info "off"
	
	api('powerOff', [], {
        sendEvent(name: 'currentState', value: 'OFF')


       	//sendEvent(name: 'lowSpeed', value: 'default')
        //sendEvent(name: 'medSpeed', value: 'default')
        //sendEvent(name: 'highSpeed', value: 'default')
    })
    }

def setLevel(value) {
	def lowThresholdvalue = (settings.lowThreshold != null && settings.lowThreshold != "") ? settings.lowThreshold.toInteger() : 33
	def medThresholdvalue = (settings.medThreshold != null && settings.medThreshold != "") ? settings.medThreshold.toInteger() : 67
	def highThresholdvalue = (settings.highThreshold != null && settings.highThreshold != "") ? settings.highThreshold.toInteger() : 99
	def valWord = value


	if (value == "LOW") { value = lowThresholdvalue }
	if (value == "MED") { value = medThresholdvalue }
	if (value == "HIGH") { value = highThresholdvalue }

	def level = Math.min(value as Integer, 99)
	
	log.trace "setLevel(value): ${level}"
  	def currentState = device.currentValue("currentState")

	if (level <= lowThresholdvalue) 
    { 
    sendEvent(name: "currentState", value: "LOW" as String, displayed: false) 
    }
	if (level >= lowThresholdvalue+1 && level <= medThresholdvalue) 
    { 
    sendEvent(name: "currentState", value: "MED" as String, displayed: false) 
    }
	if (level >= medThresholdvalue+1) 
    { 
    sendEvent(name: "currentState", value: "HIGH" as String, displayed: false) 
    }
	    transitionToSpeed(currentState, valWord)
}

def transitionToSpeed(currentState, toState)
{
	def lastSpeedState = state.lastSpeedState
	log.info "speed transition $lastSpeedState to $toState"
	if(currentState == "OFF")
    {
    	on()        
    }

	def current = 0
    def to = 0
	switch (lastSpeedState) {
	case "LOW":
		current = 1
		break
    case "MED":
		current = 2
		break
    case "HIGH":
		current = 3
		break
    default:
        current = 1
        break;
   }
   switch (toState) {
	case "LOW":
		to = 1
		break
    case "MED":
		to = 2
		break
    case "HIGH":
		to = 3
		break
   }
	state.lastSpeedState = toState
    if(current != to)
    {
    	def steps = 1
    	if(to > current)
        {
        	steps = (to - current)
        }
        else
        {
        	
            steps = ((3 - current) + to) % 3
        }
        api('speedChange', ['repeat' : steps], {
            sendEvent(name: "currentState", value: toState as String, displayed: false) 

 		})
  	}
	
}

def lowSpeed() {
	setLevel("LOW")
}

def medSpeed() {
	setLevel("MED")
}

def highSpeed() {
	setLevel("HIGH")
}

def indicatorWhenOn() {
	sendEvent(name: "indicatorStatus", value: "when on", display: false)
}

def indicatorWhenOff() {
	sendEvent(name: "indicatorStatus", value: "when off", display: false)
}

def indicatorNever() {
	sendEvent(name: "indicatorStatus", value: "never", display: false)
}

def api(method, args = [], success = {}) {

def methods = [
'powerOn': [code: fanOnCode, type: 'post'],
'powerOff': [code: fanOffCode, type: 'post'],
'speedChange': [code: fanSpeedChangeCode, type: 'post']
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
'Accept': "application/json",
'Authorization' : 'Basic '+"$username:$passwd".bytes.encodeBase64()
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

