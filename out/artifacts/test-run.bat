@echo off

:: =======================================
:: replace the following lines by your own
:: commands to launch Rome and Carthage
set r=java -jar E:\Java\RomUndKathargo\out\artifacts\jjmc_Rom\jjmc.Rom.jar
set c=java -jar E:\Java\RomUndKathargo\out\artifacts\jjmc_Rom\jjmc.Carthage.jar
:: =======================================



setlocal EnableDelayedExpansion
set n=^


set m1=__1_map_of_artificial_intelligence_competition__.txt
set m1content=9!n!V 0 R 100 100!n!V 1 N 250 100!n!V 2 N 400 100!n!V 3 N 100 250!n!V 4 N 250 250!n!V 5 N 400 250!n!V 6 N 100 400!n!V 7 N 250 400!n!V 8 C 400 400!n!E 0 1!n!E 1 2!n!E 3 4!n!E 4 5!n!E 6 7!n!E 7 8!n!E 0 3!n!E 3 6!n!E 1 4!n!E 4 7!n!E 2 5!n!E 5 8
set lp1=47810
set rp1=47810
set h1=localhost
set c1=!c! !lp1!
set r1=!r! !m1! !h1! !rp1!
echo !m1content! > !m1!
echo launching: !c1!
START !c1!
echo waiting for Carthage to bind port !lp1!
sleep 3
echo running: !r1!
!r1!
