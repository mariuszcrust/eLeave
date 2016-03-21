package com.company.eleave.rest.mapper;

public interface Mapper<D, E> {

  public D toDto(E e);

  public E toEntity(D d);
}
