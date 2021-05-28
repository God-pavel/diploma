package com.studying.diploma.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("diploma-files-bucket");
    private final String bucketName;
}