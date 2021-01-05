package fit.bikeja.dto;

import fit.bikeja.entity.Item;

public class ItemDto {

    private int id;
    private String caption;
    private Double value;
    private int createdBy_id;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.id = item.getId();
        this.caption = item.getCaption();
        this.value = item.getValue();
        this.createdBy_id = item.getCreatedBy().getId();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getCreatedBy_id() {
        return this.createdBy_id;
    }

    public void setCreatedBy_id(int createdBy_id) {
        this.createdBy_id = createdBy_id;
    }

    public Item toEntity() {
        return new Item(this.caption, this.getValue(), null);
    }
}
