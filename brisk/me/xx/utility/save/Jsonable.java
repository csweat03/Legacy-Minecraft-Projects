package me.xx.utility.save;

import com.google.gson.JsonObject;

public interface Jsonable {
    JsonObject toJson();

    void fromJson(final JsonObject jsonObj);
}
