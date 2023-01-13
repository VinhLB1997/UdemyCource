package com.spring.blogapp.response;

import com.spring.blogapp.model.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPaginationResponse {
    private List<PostDTO> content;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPage;
    private Boolean lastPage;
}
