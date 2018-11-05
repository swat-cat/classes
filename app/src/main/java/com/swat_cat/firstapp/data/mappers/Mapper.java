package com.swat_cat.firstapp.data.mappers;

public interface Mapper<F,M> {

    M from(F data);
    F to(M model);
}
