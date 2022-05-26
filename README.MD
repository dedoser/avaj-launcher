#Avaj-Launcher

##Description
Aircraft simulation program based on the UML class diagram.
![UML Diagram](avaj_uml.png)

##Behaviour
Program will take one and only one argument from the command line. This argument represents the name of a text file that will contain the scenario that needs to be
simulated. 
Executing the program will generate a file simulation.txt that describes the outcome
of the simulation.

##Compilation
Compile the project running the commands bellow in the root of your project folder.
```
$ find * -name "*.java" > sources.txt
$ javac @sources.txt
```

##Description
### Scenario file

The first line of the file contains a positive integer number. This number represents the
number of times the simulation is run. In our case, this will be the number of times a
weather change is triggered.
Each following line describes an aircraft that will be part of the simulation, with this
format: TYPE NAME LONGITUDE LATITUDE HEIGHT

### Weather Types
There are 4 types of weather:
- RAIN
- FOG
- SUN
- SNOW

Each 3 dimensional point has its own weather. Feel free to use whatever generation
algorithm you want, as long as it takes into account the point’s coordinates.

###Additional info
- Coordinates are positive numbers.
- The height is in the 0-100 range.
- If an aircraft needs to pass the upper limit height it remains at 100.
- Each time an aircraft is created, it receives a unique ID. There can’t be 2 aircrafts
with the same ID.
- If an aircraft reaches height 0 or needs to go below it, the aircraft lands, unregisters
from the weather tower and logs its current coordinates.
- When a weather change occurs, each aircraft type needs to log a message, as seen in
the example. The message format is: TYPE#NAME(UNIQUE_ID): SPECIFIC_MESSAGE.
A funny message will be appreciated during the correction.
- Each time an aircraft registers or unregisters to/from the weather tower, a message
will be logged.