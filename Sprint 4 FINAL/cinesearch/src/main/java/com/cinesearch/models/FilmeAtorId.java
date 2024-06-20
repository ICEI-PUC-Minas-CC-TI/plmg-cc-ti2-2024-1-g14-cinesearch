package com.cinesearch.models;

import java.io.Serializable;
import java.util.Objects;

public class FilmeAtorId implements Serializable {

    private Long filmeId;
    private Long atorId;

    public FilmeAtorId() {}

    public FilmeAtorId(Long filmeId, Long atorId) {
        this.filmeId = filmeId;
        this.atorId = atorId;
    }

    public Long getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(Long filmeId) {
        this.filmeId = filmeId;
    }

    public Long getAtorId() {
        return atorId;
    }

    public void setAtorId(Long atorId) {
        this.atorId = atorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmeAtorId that = (FilmeAtorId) o;
        return Objects.equals(filmeId, that.filmeId) &&
               Objects.equals(atorId, that.atorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmeId, atorId);
    }
}
