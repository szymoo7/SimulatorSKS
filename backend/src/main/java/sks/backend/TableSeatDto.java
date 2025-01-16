package sks.backend;

public class TableSeatDto {
    private Integer id;
    private int table;
    private int seat;
    private String tableSeatId;
    public TableSeatDto(Integer id, int table, int seat) {
        this.id = id;
        this.table = table;
        this.seat = seat;
        int row = (seat / 2)  + seat % 2;
        String side = seat % 2 == 0 ? "Right" : "Left";
        this.tableSeatId = "table" + table + "row" + row + "chair" + side;
    }

    public String getTableSeatId() {
        return tableSeatId;
    }

    public String isChairLeftOrRight() {
        return seat % 2 == 0 ? "right" : "left";
    }

    public Integer getId() {
        return id;
    }

    public int getTable() {
        return table;
    }

    public int getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "TableSeatDto{" +
                "id=" + id +
                ", table=" + table +
                ", seat=" + seat +
                ", tableSeatId='" + tableSeatId + '\'' +
                '}';
    }
}
