package com.avast.metrics.scala.api

trait Monitor {
  def named(name: String): Monitor
  def named(name: String, name2: String, names: String*): Monitor
  def getName: String
  def meter(name: String): Meter
  def counter(name: String): Counter
  def timer(name: String): Timer
  def gauge[A](name: String, gauge: (A) => Unit): Gauge[A]
  def histogram(name: String): Histogram
}
