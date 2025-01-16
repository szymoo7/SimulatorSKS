package sks.backend;

public class ClientDto {
    private Integer id;
    private Integer x;
    private Integer y;

    public ClientDto(Integer id, Integer x, Integer y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public ClientDto() {
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
}
