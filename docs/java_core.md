1. Throwable:
   Trong Java, Throwable là lớp cha (superclass) của tất cả các lớp đại diện cho lỗi (Error) và ngoại lệ (Exception). Nó là lớp nằm ở đỉnh của hệ thống phân cấp xử lý lỗi trong Java.
   - Tác dụng của Throwable: 
     - Dùng để bắt mọi lỗi/ngoại lệ trong khối try-catch.
       - Thường được sử dụng trong fallback methods (phương thức dự phòng) hoặc logging để ghi lại thông tin lỗi.

                public String fallbackHello(Throwable throwable) {
                // throwable có thể là bất kỳ loại Error/Exception nào
                    return "too many request";
                }