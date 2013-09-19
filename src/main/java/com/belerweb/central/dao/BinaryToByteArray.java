package com.belerweb.central.dao;

import org.bson.types.Binary;

import com.googlecode.mjorm.convert.ConversionContext;
import com.googlecode.mjorm.convert.ConversionException;
import com.googlecode.mjorm.convert.JavaType;
import com.googlecode.mjorm.convert.TypeConversionHints;
import com.googlecode.mjorm.convert.TypeConverter;

public class BinaryToByteArray implements TypeConverter<Binary, byte[]> {

  @Override
  public boolean canConvert(Class<?> sourceClass, Class<?> targetClass) {
    return Binary.class.isAssignableFrom(sourceClass) && byte[].class.isAssignableFrom(targetClass);
  }

  @Override
  public byte[] convert(Binary source, JavaType targetType, ConversionContext context,
      TypeConversionHints hints) throws ConversionException {
    return source.getData();
  }

}
