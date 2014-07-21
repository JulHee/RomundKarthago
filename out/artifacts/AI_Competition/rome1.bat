@echo off
setlocal EnableDelayedExpansion

:: global constants
:: the linefeed character; 2 blank lines are mandatory
set n=^


:: end: linefeed
set m1=__1_map_of_artificial_intelligence_competition__.txt
set m2=__2_map_of_artificial_intelligence_competition__.txt
set m3=__3_map_of_artificial_intelligence_competition__.txt
:: end: global constants

:: variables to set
:: commands to launch Rome and Carthage
set r= java -cp jjmc.seven.Rom.jar pub.ClientJar
set c=java -cp jjmc.seven.Carthage.jar pub.ServerJar
:: maps
:: set m1content=4!n!V 0 C 350 125!n!V 1 N 250 275!n!V 2 N 350 275!n!V 3 R 250 425!n!E 0 1!n!E 1 2!n!E 2 3
set m1content=8!n!V 0 R 100 100!n!V 1 N 400 100!n!V 2 N 200 200!n!V 3 N 300 200!n!V 4 N 200 300!n!V 5 N 300 300!n!V 6 N 100 400!n!V 7 C 400 400!n!E 0 1!n!E 1 7!n!E 6 7!n!E 0 6!n!E 2 3!n!E 3 5!n!E 4 5!n!E 2 4!n!E 0 2!n!E 1 3!n!E 4 6!n!E 5 7
set m2content=6!n!V 0 R 100 100!n!V 1 C 500 100!n!V 2 N 300 500!n!V 3 N 250 275!n!V 4 N 300 175!n!V 5 N 350 275!n!E 0 1!n!E 1 2!n!E 0 2!n!E 3 4!n!E 4 5!n!E 3 5!n!E 0 3!n!E 0 4!n!E 1 4!n!E 1 5!n!E 2 3!n!E 2 5
set m3content=10!n!V 0 N 300 100!n!V 1 R 100 200!n!V 2 N 300 200!n!V 3 C 500 200!n!V 4 N 200 275!n!V 5 N 400 275!n!V 6 N 250 400!n!V 7 N 350 400!n!V 8 N 150 500!n!V 9 N 450 500!n!E 0 1!n!E 0 3!n!E 1 8!n!E 3 9!n!E 8 9!n!E 4 5!n!E 5 6!n!E 2 6!n!E 2 7!n!E 4 7!n!E 0 2!n!E 1 4!n!E 3 5!n!E 6 8!n!E 7 9
:: set lp1=__LOCAL_PORT_1__
set lp1=47810
set lp2=47811
set lp3=47812
set lp4=47813
set lp5=47814
:: set rp1=__REMOTE_PORT_1__
set rp1=47830
set rp2=47831
set rp3=47832
set rp4=47833
set rp5=47834
:: set h1=__REMOTE_HOST_1__
set h1=plse.mathematik.uni-marburg.de
set h2=plse.mathematik.uni-marburg.de
set h3=plse.mathematik.uni-marburg.de
set h4=plse.mathematik.uni-marburg.de
set h5=plse.mathematik.uni-marburg.de
:: jobs 
:: set c1=!c! !lp1!
set c1=!c! !lp1!
set c2=!c! !lp2!
set c3=!c! !lp3!
set c4=!c! !lp4!
set c5=!c! !lp5!
:: set r1=!r! !m1! !h1! !rp1!
set r1=!r! !m1! !h1! !rp1!
set r2=!r! !m1! !h2! !rp2!
set r3=!r! !m1! !h3! !rp3!
set r4=!r! !m1! !h4! !rp4!
set r5=!r! !m1! !h5! !rp5!

:: create maps: do not put !n! after last line!
echo !m1content! > !m1!
echo !m2content! > !m2!
echo !m3content! > !m3!


START !r1!
START !r2!
START !r3!
START !r4!
START !r5!