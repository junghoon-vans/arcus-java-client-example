package org.example.arcus;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import net.spy.memcached.ArcusClient;
import net.spy.memcached.ConnectionFactoryBuilder;

public class ArcusWrapper<T> {
  private static final String DEFAULT_ADDRESS = "localhost:2181";
  private static final String DEFAULT_SERVICE_CODE = "test";
  private static final int EXPIRE_TIME = 60;
  private static final int OPERATION_TIMEOUT = 700;

  private final String address;
  private final String serviceCode;
  private ArcusClient arcusClient;

  public ArcusWrapper() {
    this.address = DEFAULT_ADDRESS;
    this.serviceCode = DEFAULT_SERVICE_CODE;
  }

  public ArcusWrapper(String address, String serviceCode) {
    this.address = address;
    this.serviceCode = serviceCode;
  }

  public void connect() {
    ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
    arcusClient = ArcusClient.createArcusClient(address, serviceCode, connectionFactoryBuilder);
  }

  public void close() {
    arcusClient.shutdown();
  }

  public boolean insert(String key, T value) {
    Future<Boolean> future = arcusClient.set(key, EXPIRE_TIME, value);
    try {
      return future.get(OPERATION_TIMEOUT, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      future.cancel(true);
    }
    return false;
  }

  public Optional<T> get(String key) {
    Future<Object> future = arcusClient.asyncGet(key);
    try {
      T result = (T) future.get(OPERATION_TIMEOUT, TimeUnit.MILLISECONDS);
      return Optional.of(result);
    } catch (Exception e) {
      future.cancel(true);
    }
    return Optional.empty();
  }

  public boolean deleteAll() {
    Future<Boolean> future = arcusClient.flush();
    try {
      return future.get(OPERATION_TIMEOUT, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      future.cancel(true);
    }
    return false;
  }
}
