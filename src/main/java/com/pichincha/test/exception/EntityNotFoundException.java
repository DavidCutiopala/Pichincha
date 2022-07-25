package com.pichincha.test.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class EntityNotFoundException extends RuntimeException{

	 /**
    *
    */
   private static final long serialVersionUID = 1L;

   public EntityNotFoundException(Class<?> _class, Object... searchParamsMap) {
       super(EntityNotFoundException.generateMessage(_class.getSimpleName(),
               toMap(String.class, String.class, searchParamsMap)));
   }

   public EntityNotFoundException(Class<?> _class) {
       super("No existen datos en la tabla relacionada con ".concat(_class.getSimpleName()));
   }

   private static String generateMessage(String entity, Map<String, String> searchParams) {
       return StringUtils.capitalize(entity) + " no se encontró para los parámetros " + searchParams;
   }

   private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries) {
       if (entries.length % 2 == 1)
           throw new IllegalArgumentException("Entradas inválidas");
       return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
               (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1].toString())), Map::putAll);
   }

}
