package animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {
    
    private String data;
    private TreeNode yes;
    private TreeNode no;

    public TreeNode() {
    }

    public TreeNode(final String data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return no == null && yes == null;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public TreeNode getYes() {
        return yes;
    }

    public void setYes(final TreeNode yes) {
        this.yes = yes;
    }

    public TreeNode getNo() {
        return no;
    }

    public void setNo(final TreeNode no) {
        this.no = no;
    }
    
}
