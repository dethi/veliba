package epita.com.veliba.station;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StationItem {
    @SerializedName("recordid")
    public String recordId;

    public Fields fields;

    @Override
    public String toString() {
        return String.format("StationItem{recordId: %s, status: %s, bikeStands: %d/%d, name: %s," +
                        "address: %s, lastUpdate: %s}", recordId, fields.status, fields.bikeStands,
                fields.availableBikeStands, fields.name, fields.address, fields.lastUpdate);
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
    }

    public enum Status {
        OPEN(true), CLOSED(false);

        public boolean value;

        Status(boolean value) {
            this.value = value;
        }
    }
}
