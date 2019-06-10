package types; /**
 * Copyright (C) 2015, BMW Car IT GmbH
 * Author: Stefan Holder (stefan.holder@bmw.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import com.bmw.mapmatchingutils.astar.beans.Foot_Data;
import com.bmw.mapmatchingutils.astar.beans.Road_Data;
import types.Point;

import javax.annotation.Nonnull;

/**
 * Default type to represent the position of a vehicle on the road network.
 * It is also possible to use a custom road position class instead.
 */
public class RoadPosition implements Comparable<RoadPosition>{

    /**
     * ID of the edge, on which the vehicle is positioned.
     */
    public final long edgeId;

    /**
     * Position on the edge from beginning as a number in the interval [0,1].
     */
    public final double fraction;

    public final double distance;

    public final Point position;

    public final Road_Data road_data;

    public RoadPosition(long edgeId, double fraction, double distance, Point position, Road_Data road_data) {
        if (fraction < 0.0 || fraction > 1.0) {
            throw new IllegalArgumentException();
        }

        this.edgeId = edgeId;
        this.fraction = fraction;
        this.distance = distance;
        this.position = position;
        this.road_data = road_data;
    }

    public RoadPosition(long edgeId, double fraction, double distance, double x, double y, Road_Data road_data) {
        this(edgeId, fraction, distance, new Point(x, y), road_data);
    }

    @Override
    public String toString() {
        return "RoadPosition{" +
                "edgeId=" + edgeId +
                ", fraction=" + fraction +
                ", position=" + position +
                ", road_data=" + road_data +
                '}';
    }

    @Override
    public int compareTo(RoadPosition o) {
        return Double.compare(this.distance, o.distance);
    }
}
