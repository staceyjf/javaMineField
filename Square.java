public class Square {
    private Boolean hasBeenPlayed;
    private Boolean hasBomb;

    // initialize the square to neutral
    public Square() {
        this.hasBeenPlayed = false;
        this.hasBomb = false;
    }

    // check to see if square has been played
    public Boolean getPlayState() {
        return this.hasBeenPlayed;
    }

    // check to see if square has a bomb
    public Boolean getBombState() {
        return this.hasBomb;
    }

    // set the state of play status
    public void setPlayState(Boolean isPlayed) {
        this.hasBeenPlayed = isPlayed;
    }

    // check to see if square has a bomb
    public void setBombState(Boolean isBomb) {
        this.hasBomb = isBomb;
    }

}
