package net.philadams.onesixtyone;

/**
 * Created by phil on 3/18/15.
 */
public class OneSixtyOneThing {

  public enum Status {NOT_DONE, DONE, WILL_NOT_DO}

  public int thingId;
  public String description;
  public Status status;

  public OneSixtyOneThing(int thingId, String description, Status status) {
    this.thingId = thingId;
    this.description = description;
    this.status = status;
  }

}
