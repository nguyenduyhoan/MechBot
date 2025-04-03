# MechBot
1. Quá trình Đăng ký Người dùng (Register)
   📌 Mục tiêu: Nhận thông tin người dùng, mã hóa mật khẩu và lưu vào database.

👉 Các bước:

Người dùng gửi yêu cầu đăng ký (POST /api/auth/register).

AuthController nhận request và gọi AuthService để xử lý logic đăng ký.

Mã hóa mật khẩu bằng BCrypt (PasswordEncoder).

Lưu thông tin người dùng vào database (UserRepository).

Gán quyền (Role) cho người dùng (RoleRepository).

Trả về phản hồi thành công hoặc lỗi.

📌 Class liên quan:

AuthController.java

AuthService.java

UserRepository.java

RoleRepository.java

User.java

PasswordEncoderUtils.java

GlobalExceptionHandler.java (Xử lý lỗi nếu có)

2. Quá trình Đăng nhập Người dùng (Login)
   📌 Mục tiêu: Xác thực người dùng và tạo JWT Token.

👉 Các bước:

Người dùng gửi yêu cầu đăng nhập (POST /api/auth/login).

AuthController nhận request và gọi AuthService để xử lý xác thực.

Tìm người dùng trong database (UserRepository).

Kiểm tra mật khẩu bằng BCrypt (PasswordEncoderUtils).

Nếu hợp lệ, tạo JWT Token (JwtUtils).

Trả về JWT Token cho người dùng.

📌 Class liên quan:

AuthController.java

AuthService.java

UserRepository.java

PasswordEncoderUtils.java

CustomAuthenticationProvider.java

UserDetailsServiceImpl.java

JwtUtils.java

GlobalExceptionHandler.java (Xử lý lỗi nếu có)

3. Xác thực và Bảo mật với JWT
   📌 Mục tiêu: Bảo vệ API bằng JWT, chặn yêu cầu không hợp lệ.

👉 Các bước:

Người dùng gửi request đến API được bảo vệ.

JwtFilter kiểm tra JWT trong request header.

Giải mã và xác thực JWT (JwtUtils).

Nếu hợp lệ, cho phép truy cập API.

Nếu không hợp lệ, trả về lỗi 401 Unauthorized.

📌 Class liên quan:

JwtFilter.java

JwtTokenFilter.java

JwtUtils.java

JwtAuthenticationEntryPoint.java

SecurityConfig.java

4. Xử lý Ngoại lệ
   📌 Mục tiêu: Quản lý lỗi xác thực và bảo mật.

👉 Các lỗi có thể xảy ra:

Token hết hạn (ExpiredJwtException).

Token sai định dạng (MalformedJwtException).

Không có quyền truy cập (AccessDeniedException).

📌 Class liên quan:

CustomException.java

GlobalExceptionHandler.java

JwtAuthenticationEntryPoint.java

5. Cấu hình Security (SecurityConfig.java)
   📌 Mục tiêu: Cấu hình bảo mật cho toàn bộ ứng dụng.

👉 Các bước:

Vô hiệu hóa CSRF (csrf().disable()).

Bật xác thực stateless (sessionManagement().sessionCreationPolicy(STATELESS)).

Định nghĩa quyền truy cập API:

/api/auth/** → Public (không cần đăng nhập).

/api/admin/** → Chỉ ADMIN có quyền.

/api/user/** → USER & ADMIN có quyền.

Thêm JwtFilter để kiểm tra token trước khi xử lý request.

Xử lý lỗi xác thực với JwtAuthenticationEntryPoint.

Tóm tắt Toàn bộ Flow
Đăng ký: Người dùng gửi thông tin → Lưu vào DB → Mã hóa mật khẩu → Trả về kết quả.

Đăng nhập: Nhận username/password → Kiểm tra DB → Tạo JWT → Trả về token.

Xác thực JWT: Người dùng gửi request → Kiểm tra JWT → Nếu hợp lệ, cho phép truy cập API.

Bảo mật: Dùng Spring Security để kiểm soát quyền truy cập API.

Xử lý lỗi: Khi có lỗi xác thực → Trả về mã lỗi 401 Unauthorized.