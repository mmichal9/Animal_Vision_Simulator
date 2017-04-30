#ifdef GL_SL
precision mediump float;
#endif

uniform vec3                iResolution;
uniform float               iGlobalTime;
uniform sampler2D           iChannel0;
varying vec2                texCoord;


// Created by mmichal

vec4 cat_color(in vec4 col){

	vec3 c_r = vec3(0.625, 0.375, 0.0);
    vec3 c_g = vec3(0.70, 0.30, 0.0);
	vec3 c_b = vec3(0.0, 0.30, 0.70);

	vec3 rgb = vec3( dot(col.rgb,c_r), dot(col.rgb,c_g), dot(col.rgb,c_b) );
    return vec4(rgb, 1.);
}

float remap(float value, float inputMin, float inputMax, float outputMin, float outputMax)
{
    return (value - inputMin) * ((outputMax - outputMin) / (inputMax - inputMin)) + outputMin;
}
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
	vec2 uv = fragCoord.xy / iResolution.xy;
	float normalizedContrast = 0.3;
	float contrast = remap(normalizedContrast, 0.0, 1.0, 0.2 /*min*/, 4.0 /*max*/);

	vec4 srcColor = texture2D(iChannel0, uv);
	vec4 dstColor = vec4((srcColor.rgb - vec3(0.5)) * contrast + vec3(0.5), 1.0);
	vec4 col = clamp(dstColor, 0.0, 1.0);

    fragColor =  cat_color(col);

}

void main() {
    mainImage(gl_FragColor, texCoord.xy*iResolution.xy);
}