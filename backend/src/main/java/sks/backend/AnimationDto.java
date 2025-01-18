package sks.backend;

public class AnimationDto {

    private boolean forTable = false;
    private Integer id;
    private Integer x;
    private Integer y;
    private int table;
    private int seat;
    private String tableSeatId;

    public AnimationDto(Integer id, Integer x, Integer y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public AnimationDto(Integer id, int table, int seat, boolean forTable) {
        this.id = id;
        int row = (seat / 2)  + seat % 2;
        String side = seat % 2 == 0 ? "Right" : "Left";
        this.tableSeatId = "table" + table + "row" + row + "chair" + side;
        this.forTable = forTable;
    }

    public AnimationDto(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String getTableSeatId() {
        return tableSeatId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean isForTable() {
        return forTable;
    }

    public int getTable() {
        return table;
    }

    public int getSeat() {
        return seat;
    }
}
