package Classes;

/**
 * Created by Alvan on 1/30/2018.
 */

public class SkillItem {

    private String head;
    private int image;

    public SkillItem(String head, int image){

        this.head = head;
        this.image= image;

    }

    public String getHead(){ return head; }
    public int getImage() { return image; }

}
