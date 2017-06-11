package epita.com.veliba.station;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class StationItem {
    @SerializedName("recordid")
    public String id;

    public Fields fields;

    @Override
    public String toString() {
        return String.format("StationItem{id: %s, status: %s, bikeStands: %d/%d, name: %s," +
                        "address: %s, lastUpdate: %s, long: %f, lat: %f}", id, fields.status, fields.bikeStands,
                        fields.availableBikeStands, fields.name, fields.address, fields.lastUpdate, fields.position.get(0), fields.position.get(1));
    }

    public class Fields {
        public String name;
        public String address;
        public Status status;

        @SerializedName("bike_stands")
        public int bikeStands;

        @SerializedName("available_bike_stands")
        public int availableBikeStands;

        @SerializedName("last_update")
        public Date lastUpdate;

        @SerializedName("position")
        public List<Double> position = null;
    }

    public enum Status {
        OPEN(true), CLOSED(false);

        public boolean value;

        Status(boolean value) {
            this.value = value;
        }
    }
}
