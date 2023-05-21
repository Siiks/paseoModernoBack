package com.example.paseomodernobk.Entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ImageData {
    private Long id;
    private byte[] bytes;
}
