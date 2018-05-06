package cloud.lohmann.haushaltsplan.domain

class HousekeepingTask : Comparable<HousekeepingTask>{
    override fun compareTo(other: HousekeepingTask): Int {
        if(other.finished == this.finished)
            return 0
        else if(!other.finished)
            return 1
        else
            return -1;

    }

    var name: String = "";
    var finished: Boolean = false;
    var interval: TaskInterval = TaskInterval.DAILY;

    constructor() {}

    constructor(name: String, finished: Boolean, interval: TaskInterval) {
        this.name = name
        this.finished = finished
        this.interval = interval
    }
}