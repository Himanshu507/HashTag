package com.prag.hashtags;

class Add_item_model {
    String Text;

    public Add_item_model() {
    }

    public Add_item_model(String Text) {
      this.Text = Text;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        this.Text = text;
    }
}
