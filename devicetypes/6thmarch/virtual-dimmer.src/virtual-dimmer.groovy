/**
 *  Alexa Helper-Virtual Dimmer
 *
 * 	Copyright 2016 Benjamin Yam
 *	
 *
 *	Modified from :
 *  Copyright 2015 Michael Struck
 *  Version 1.0.0 11/27/15
 * 
 *  Uses code from SmartThings
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
 */

metadata {
        definition (name: "Virtual Dimmer", namespace: "6thmarch", author: "Benjamin Yam") {
        capability "Switch"
        capability "Refresh"
        capability "Switch Level"
    }

	// simulator metadata
	simulator {
	}
    
    preferences {
       
       input "initialDimmerValue", "number", title: "Initial Dimmer Value",
              description: "Dimmer value the device starts with when device is turned on. (For devices which resets the dimmer value when turned off and on again.)",
              required: false, displayDuringSetup: true, range: 0..100
    }

	// UI tile definitions
	tiles {
		standardTile("button", "device.switch", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: 'Off', action: "switch.on", icon: "st.Kids.kid10", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', action: "switch.off", icon: "st.Kids.kid10", backgroundColor: "#79b821", nextState: "off"
		}
		standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}        
        controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 2, inactiveLabel: false, backgroundColor:"#ffe71e") {
            state "level", action:"switch level.setLevel"
        }
        valueTile("lValue", "device.level", inactiveLabel: true, height:1, width:1, decoration: "flat") {
            state "levelValue", label:'${currentValue}%', unit:"", backgroundColor: "#53a7c0"
        }
		main(["button"])
		details(["button", "refresh","levelSliderControl","lValue"])
	}
}

def parse(String description) {

}

def on() {
	sendEvent(name: "switch", value: "on")
    log.info "Dimmer On"
}

def off() {


    sendEvent(name: "switch", value: "off")
    log.info "Dimmer Off"
    if(initialDimmerValue != null && initialDimmerValue >= 0 && initialDimmerValue <= 100)
    {
    	sendEvent(name:"level",value:initialDimmerValue)
 		//log.debug "initialDimValue: " + initialDimmerValue
    }
    
}

def setLevel(val){
	log.info "oldLevel ${device.currentValue("level")}"
	def oldLevel = device.currentValue("level")

    log.info "setLevel $val"
    // make sure we don't drive switches past allowed values (command will hang device waiting for it to
    // execute. Never commes back)
    if (val < 0){
    	val = 0
    }
    
    if( val > 100){
    	val = 100
    }
    
    if (val == 0){ 
    	sendEvent(name:"level",value:val)
    	off()
    }
    else
    {
    	on()
    	sendEvent(name:"level",value:val)
        sendEvent(name:"oldLevel",value:oldLevel)
    	sendEvent(name:"switch.setLevel",value:val)
    }
}

def refresh() {
    log.info "refresh"
}