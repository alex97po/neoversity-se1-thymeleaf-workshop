package com.pohorelov.model;

/**
 * Один варіант відповіді на питання.
 * key — буква (A, B, C, D), text — текст варіанту.
 */
public record Answer(String key, String text) {}
