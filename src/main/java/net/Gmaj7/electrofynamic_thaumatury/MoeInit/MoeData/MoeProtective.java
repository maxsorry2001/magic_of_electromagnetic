package net.Gmaj7.electrofynamic_thaumatury.MoeInit.MoeData;

public class MoeProtective {
    private float protecting;

    public MoeProtective(float num){
        this.protecting = num;
    }

    public float getProtecting() {
        return protecting;
    }

    public void setProtecting(float protecting) {
        this.protecting = protecting > 0 ? protecting : 0;
    }
}
