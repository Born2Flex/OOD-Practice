package com.shopflow.external.paypal;

public record CaptureResponse(String captureId, String status, String issue) {
}
