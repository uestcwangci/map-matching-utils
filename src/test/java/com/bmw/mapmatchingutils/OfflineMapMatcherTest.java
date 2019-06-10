/**
 * Copyright (C) 2015-2016, BMW Car IT GmbH and BMW AG
 * Author: Stefan Holder (stefan.holder@bmw.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain aStar copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bmw.mapmatchingutils;

import com.bmw.hmm.SequenceState;
import com.bmw.hmm.Transition;
import com.bmw.hmm.ViterbiAlgorithm;
import com.bmw.mapmatchingutils.astar.*;
import com.bmw.mapmatchingutils.astar.beans.Foot_Data;
import com.bmw.mapmatchingutils.astar.beans.PointData;
import com.bmw.mapmatchingutils.astar.beans.Road_Data;
import org.junit.BeforeClass;
import org.junit.Test;
import types.GpsMeasurement;
import types.Point;
import types.RoadPath;
import types.RoadPosition;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * FootData -> RoadPosition
 *
 */

/**
 * This class demonstrate how to use the hmm-lib for map matching. The methods
 * of this class can be used as aStar template to implement map matching for an actual map.
 * <p>
 * The test scenario is depicted in ./OfflineMapMatcherTest.png.
 * All road segments can be driven in both directions. The orientation of road segments
 * is needed to determine the fractions of aStar road positions.
 */
public class OfflineMapMatcherTest {

    private final HmmProbabilities hmmProbabilities = new HmmProbabilities();

    private final static Map<GpsMeasurement, Collection<RoadPosition>> candidateMap =
            new HashMap<>();

    private final static Map<Transition<RoadPosition>, Double> routeLengths = new HashMap<>();

    private static final DBHelper dbHelper = new DBHelper();
    private static final Map<Road_Data, Double> roadData = dbHelper.query_road(); //
    private static final Map<PointData, List<PointData>> pointData = dbHelper.query_point(); //
    private static final A_star_getDistance aStar = new A_star_getDistance();

//    private final static GpsMeasurement gps1 = new GpsMeasurement(seconds(0), 10, 10);
//    private final static GpsMeasurement gps2 = new GpsMeasurement(seconds(1), 30, 20);
//    private final static GpsMeasurement gps3 = new GpsMeasurement(seconds(2), 30, 40);
//    private final static GpsMeasurement gps4 = new GpsMeasurement(seconds(3), 10, 70);
    //test1
//    private final static GpsMeasurement gps1 = new GpsMeasurement(seconds(0), 1.5, 3.5);
//    private final static GpsMeasurement gps2 = new GpsMeasurement(seconds(1), 3.0, 3.0);
//    private final static GpsMeasurement gps3 = new GpsMeas urement(seconds(2), 3.5, 6.7);
//    private final static GpsMeasurement gps4 = new GpsMeasurement(seconds(3), 4.2, 8.2);
//    private final static GpsMeasurement gps5 = new GpsMeasurement(seconds(4), 8.0, 8.55);
    //test2
    private final static GpsMeasurement gps1 = new GpsMeasurement(seconds(0), 61.0120843301571, 25.9074895613654);
    private final static GpsMeasurement gps2 = new GpsMeasurement(seconds(1), 60.6874495359214, 25.7384059463668);
    private final static GpsMeasurement gps3 = new GpsMeasurement(seconds(2), 59.3541867181996, 24.2065205168655);
    private final static GpsMeasurement gps4 = new GpsMeasurement(seconds(3), 59.5520476288333, 26.1042824416387);
    private final static GpsMeasurement gps5 = new GpsMeasurement(seconds(4), 60.2403635795241, 26.2505680606862);

    private final static List<GpsMeasurement> gpsList = new ArrayList<>();
    private final static List<List<RoadPosition>> roadPointList = new ArrayList<>();




    /**
     * 线段是有向线段，fraction代表从起点开始到路径点所占比例
     */
//    private final static RoadPosition rp11 = new RoadPosition(1, 1.0 / 5.0, 20.0, 10.0);
//    private final static RoadPosition rp12 = new RoadPosition(2, 1.0 / 5.0, 60.0, 10.0);
//    private final static RoadPosition rp21 = new RoadPosition(1, 2.0 / 5.0, 20.0, 20.0);
//    private final static RoadPosition rp22 = new RoadPosition(2, 2.0 / 5.0, 60.0, 20.0);
//    private final static RoadPosition rp31 = new RoadPosition(1, 5.0 / 6.0, 20.0, 40.0);
//    private final static RoadPosition rp32 = new RoadPosition(3, 1.0 / 4.0, 30.0, 50.0);
//    private final static RoadPosition rp33 = new RoadPosition(2, 5.0 / 6.0, 60.0, 40.0);
//    private final static RoadPosition rp41 = new RoadPosition(4, 2.0 / 3.0, 20.0, 70.0);
//    private final static RoadPosition rp42 = new RoadPosition(5, 2.0 / 3.0, 60.0, 70.0);


//    private final static Foot_Data rp11 = new Foot_Data(5, 2.0 / 3.0, 60.0);



    @BeforeClass
    public static void setUpClass() {
//        candidateMap.put(gps1, Arrays.asList(rp11, rp12));
//        candidateMap.put(gps2, Arrays.asList(rp21, rp22));
//        candidateMap.put(gps3, Arrays.asList(rp31, rp32, rp33));
//        candidateMap.put(gps4, Arrays.asList(rp41, rp42));

        gpsList.add(gps1);
        gpsList.add(gps2);
        gpsList.add(gps3);
        gpsList.add(gps4);
        gpsList.add(gps5);
        //计算垂足点
        List<RoadPosition> list;
        for (GpsMeasurement gps : gpsList) {
            list = location2road(gps, roadData);
            roadPointList.add(list);
            candidateMap.put(gps, list);
        }



//        List<RoadPosition> roadPositions = new ArrayList<>();
//        roadPositions.add(rp11);
//        roadPositions.add(rp12);
//        roadPointList.add(roadPositions);
//        roadPositions = new ArrayList<>();
//        roadPositions.add(rp21);
//        roadPositions.add(rp22);
//        roadPointList.add(roadPositions);
//        roadPositions = new ArrayList<>();
//        roadPositions.add(rp31);
//        roadPositions.add(rp32);
//        roadPositions.add(rp33);
//        roadPointList.add(roadPositions);
//        roadPositions = new ArrayList<>();
//        roadPositions.add(rp41);
//        roadPositions.add(rp42);
//        roadPointList.add(roadPositions);

        for (int i = 0; i < roadPointList.size() - 1; i++) {
            computeDistance(roadPointList.get(i), roadPointList.get(i + 1));
        }

        // TODO 这里的routeLength需要根据2点算出来
//        addRouteLength(rp11, rp21, 10.0);
//        addRouteLength(rp11, rp22, 110.0);
//        addRouteLength(rp12, rp21, 110.0);
//        addRouteLength(rp12, rp22, 10.0);
//
//        addRouteLength(rp21, rp31, 20.0);
//        addRouteLength(rp21, rp32, 40.0);
//        addRouteLength(rp21, rp33, 80.0);
//        addRouteLength(rp22, rp31, 80.0);
//        addRouteLength(rp22, rp32, 60.0);
//        addRouteLength(rp22, rp33, 20.0);
//
//        addRouteLength(rp31, rp41, 30.0);
//        addRouteLength(rp31, rp42, 70.0);
//        addRouteLength(rp32, rp41, 30.0);
//        addRouteLength(rp32, rp42, 50.0);
//        addRouteLength(rp33, rp41, 70.0);
//        addRouteLength(rp33, rp42, 30.0);
    }

    private static void computeDistance(List<RoadPosition> prePositions, List<RoadPosition> nextPositions) {
        for (RoadPosition prePosition : prePositions) {
            for (RoadPosition nextPosition : nextPositions) {
                addRouteLength(prePosition, nextPosition, computeDistance(prePosition, nextPosition));
            }
        }
    }

    private static double computeDistance(RoadPosition prePosition, RoadPosition nextPosition) {
        /**
         * 1. 找到这两个点所在路径;
         * 2. 找出两条路径的端点AB和CD;
         * 3. 使用A*计算AC,AD,BC和BD,以及相应的四组node_list;
         * 4. 只保留路径长度最长的一组node_list;
         * 5. 把这组node_list的去掉端点后起点换成s1,终点换成s2.
         * 6. 从s1开始，沿径计算node_list到s2的长度
         */
        aStar.initData(roadData, pointData);
//        Foot_Data start = new Foot_Data(1.0, 1.0, 0.0, new Road_Data(1, 0.4, 1, 3.3));
//        Foot_Data goal1 = new Foot_Data(1.0, 2.0, 0.0, new Road_Data(1, 0.4, 1, 3.3));
//        aStar.init(start, goal1);
        aStar.init(prePosition, nextPosition);
//        System.out.println(aStar.getDistance());

//        Foot_Data goal2 = new Foot_Data(1.0, 8.5, 0.0, new Road_Data(1, 8.05, 1, 10));
//        aStar.init(start, goal2);
//        System.out.println(aStar.getDistance());
        return aStar.getDistance();
    }

    private static Date seconds(int seconds) {
        Calendar c = new GregorianCalendar(2019, 1, 1);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }



    private static void addRouteLength(RoadPosition from, RoadPosition to, double routeLength) {
        routeLengths.put(new Transition<RoadPosition>(from, to), routeLength);
    }

    /*
     * Returns the Cartesian distance between two points.
     * For real map matching applications, one would compute the great circle distance between
     * two GPS points.
     */
    private double computeDistance(Point p1, Point p2) {
        final double xDiff = p1.x - p2.x;
        final double yDiff = p1.y - p2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    /*
     * For real map matching applications, candidates would be computed using aStar radius query.
     */
    private Collection<RoadPosition> computeCandidates(GpsMeasurement gpsMeasurement) {
        return candidateMap.get(gpsMeasurement);
    }

    private void computeEmissionProbabilities(
            TimeStep<RoadPosition, GpsMeasurement, RoadPath> timeStep) {
        for (RoadPosition candidate : timeStep.candidates) {
            final double distance =
                    computeDistance(candidate.position, timeStep.observation.position);
            timeStep.addEmissionLogProbability(candidate,
                    hmmProbabilities.emissionLogProbability(distance));
        }
    }

    private void computeTransitionProbabilities(
            TimeStep<RoadPosition, GpsMeasurement, RoadPath> prevTimeStep,
            TimeStep<RoadPosition, GpsMeasurement, RoadPath> timeStep) {
        final double linearDistance = computeDistance(prevTimeStep.observation.position,
                timeStep.observation.position);
        final double timeDiff = (timeStep.observation.time.getTime() -
                prevTimeStep.observation.time.getTime()) / 1000.0;

        for (RoadPosition from : prevTimeStep.candidates) {
            for (RoadPosition to : timeStep.candidates) {

                // For real map matching applications, route lengths and road paths would be
                // computed using aStar router. The most efficient way is to use aStar single-source
                // multi-target router.
                final double routeLength = routeLengths.get(new Transition<>(from, to));
                timeStep.addRoadPath(from, to, new RoadPath(from, to));

                final double transitionLogProbability = hmmProbabilities.transitionLogProbability(
                        routeLength, linearDistance, timeDiff);
                timeStep.addTransitionLogProbability(from, to, transitionLogProbability);
            }
        }
    }

    //定点到线 直接更新location的坐标 算法复杂度 O(N*logN)
    public static List<RoadPosition> location2road(GpsMeasurement gps, Map<Road_Data, Double> roadData) {


        double x = gps.position.x;
        double y = gps.position.y;
        List<RoadPosition> FootList = new ArrayList<>();

        // 算法复杂度 O(N)
        for (Map.Entry<Road_Data, Double> entry : roadData.entrySet()) {
            Road_Data road_data = entry.getKey();
            double distance;
            double a = road_data.getA();
            double b = road_data.getB();
            double c = road_data.getC();
            double x1 = road_data.getPoint1x();
            double x2 = road_data.getPoint2x();
            double y1 = road_data.getPoint1y();
            double y2 = road_data.getPoint2y();
            double footx = (b * b * x - a * b * y - a * c) / (a * a + b * b);//垂足x
            double footy = (a * a * y - a * b * x - b * c) / (a * a + b * b);//垂足y
            //如果垂足在线段point1_point2上
            if (road_data.getPoint1x() <= footx && footx <= road_data.getPoint2x()
                    && road_data.getPoint1y() <= footy && footy <= road_data.getPoint2y()) {
                distance = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
                double l1 = Math.sqrt((footx - x1) * (footx - x1) + (footy - y1) * (footy - y1));
                double l2 = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
                double scale = l1 / l2;
//                RoadPosition foot_data = new RoadPosition(footx, footy, distance, scale, road_data);
                RoadPosition foot_data = new RoadPosition(road_data.getId(), scale, distance, footx, footy, road_data);
                FootList.add(foot_data);
            }
        }
        // 算法复杂度 O(N*logN)
        Collections.sort(FootList);
        while (FootList.size() > 3) {
            FootList.remove(FootList.size() - 1);
        }
        return FootList;
    }


    @Test
    public void testMapMatching() {

        ViterbiAlgorithm<RoadPosition, GpsMeasurement, RoadPath> viterbi =
                new ViterbiAlgorithm<>();
        TimeStep<RoadPosition, GpsMeasurement, RoadPath> prevTimeStep = null;
        for (GpsMeasurement gpsMeasurement : gpsList) {
            final Collection<RoadPosition> candidates = computeCandidates(gpsMeasurement);
            final TimeStep<RoadPosition, GpsMeasurement, RoadPath> timeStep =
                    new TimeStep<>(gpsMeasurement, candidates);
            computeEmissionProbabilities(timeStep);
            if (prevTimeStep == null) {
                viterbi.startWithInitialObservation(timeStep.observation, timeStep.candidates,
                        timeStep.emissionLogProbabilities);
            } else {
                computeTransitionProbabilities(prevTimeStep, timeStep);
                viterbi.nextStep(timeStep.observation, timeStep.candidates,
                        timeStep.emissionLogProbabilities, timeStep.transitionLogProbabilities,
                        timeStep.roadPaths);
            }
            prevTimeStep = timeStep;
        }

        List<SequenceState<RoadPosition, GpsMeasurement, RoadPath>> roadPositions =
                viterbi.computeMostLikelySequence();
//        System.out.println(roadPositions);
        for (SequenceState<RoadPosition, GpsMeasurement, RoadPath> position : roadPositions) {
//            System.out.println("observation: " + position.observation.position);
            System.out.println("expected: " + position.state.position);
//            if (position.transitionDescriptor != null) {
//                System.out.print("road - ");
//                System.out.print("from:" + position.transitionDescriptor.from.position);
//                System.out.println("    to:" + position.transitionDescriptor.to.position);
//            }
        }
        assertFalse(viterbi.isBroken());
//        List<SequenceState<RoadPosition, GpsMeasurement, RoadPath>> expected = new ArrayList<>();
//        expected.add(new SequenceState<>(rp11, gps1, (RoadPath) null));
//        expected.add(new SequenceState<>(rp21, gps2, new RoadPath(rp11, rp21)));
//        expected.add(new SequenceState<>(rp31, gps3, new RoadPath(rp21, rp31)));
//        expected.add(new SequenceState<>(rp41, gps4, new RoadPath(rp31, rp41)));
//        assertEquals(expected, roadPositions);
    }






}
