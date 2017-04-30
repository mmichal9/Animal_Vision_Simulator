#extension GL_OES_standard_derivatives : enable
precision mediump float;

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


vec3 rgb2hsv(vec3 c)
{
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c)
{
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
	return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

vec3 blackwhite(vec3 d ){

	vec3 c_r = vec3(0.3, 0.4, 0.3);
	vec3 c_g = vec3(0.3, 0.4, 0.3);
	vec3 c_b = vec3(0.3, 0.4, 0.3);

	d = vec3( dot(d.rgb,c_r), dot(d.rgb,c_g), dot(d.rgb,c_b) );

	return d;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;
    float t = 0.5+ 0.5*cos(iGlobalTime/4.);

    vec4 color = texture2D(iChannel0, uv);
    vec3 colorBW = blackwhite(color.rgb);

    vec3 fragHSV = rgb2hsv(color.rgb);

    float H = fragHSV.r;
    float S = fragHSV.g;
    float V = fragHSV.b;

    vec3 newRGB = vec3(H,S,V);

    float minVal = 0.0001;
    float maxVal = 0.1500;

    if(t < 1.0 ){
        minVal = minVal + t;
    	maxVal = maxVal + t;}
    else{
        minVal = minVal - t;
    	maxVal = maxVal - t;}

    float variable = newRGB.r;
    float range = clamp(variable, minVal, maxVal);

    if (newRGB.r != range){

        newRGB = rgb2hsv(colorBW);
    }

    newRGB = hsv2rgb(newRGB);

	fragColor = vec4(newRGB,1.0);
}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}
